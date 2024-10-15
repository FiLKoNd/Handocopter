package com.filkond.handocopter.client;

import com.filkond.handocopter.HandocopterConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class HandocopterClient implements ClientModInitializer {
    public static HandocopterConfig CONFIG;
    @Override
    public void onInitializeClient() {
        AutoConfig.register(HandocopterConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(HandocopterConfig.class).getConfig();
    }
}
