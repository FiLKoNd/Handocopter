package com.filkond.handocopter;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.common.ClientOptionsC2SPacket;
import net.minecraft.network.packet.c2s.common.SyncedClientOptions;
import net.minecraft.util.Arm;
import org.lwjgl.glfw.GLFW;

public class HandocopterClient implements ClientModInitializer {
    public static HandocopterConfig CONFIG;
    private static Arm lastArm = Arm.RIGHT;
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(HandocopterConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(HandocopterConfig.class).getConfig();
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.handocopter.open_settings", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_RIGHT_SHIFT, // The keycode of the key
                "category.handocopter.main" // The translation key of the keybinding's category.
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                client.setScreen(AutoConfig.getConfigScreen(HandocopterConfig.class, client.currentScreen).get());
            }
        });
    }

    public static void changeArm() {
        lastArm = lastArm.getOpposite();
        final var mc = MinecraftClient.getInstance();
        final var networkHandler = mc.getNetworkHandler();
        final var options = mc.options.getSyncedOptions();
        if (networkHandler == null) return;
        networkHandler.sendPacket(
                new ClientOptionsC2SPacket(
                        new SyncedClientOptions(
                                options.language(),
                                options.viewDistance(),
                                options.chatVisibility(),
                                options.chatColorsEnabled(),
                                options.playerModelParts(),
                                lastArm,
                                options.filtersText(),
                                options.allowsServerListing()
                        )
                )
        );
    }
}
