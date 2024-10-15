package com.filkond.handocopter;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "handocopter")
public class HandocopterConfig implements ConfigData {
    public boolean isEnabled = true;
    public long delay = 2000L;
}
