package com.filkond.handocopter.mixin;

import com.filkond.handocopter.HandocopterClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @ModifyVariable(method = "renderHotbar", at = @At(value = "STORE"))
    private Arm modifyArm(Arm original) {
        if (!HandocopterClient.CONFIG.isEnabled) return original;
        Arm arm = MinecraftClient.getInstance().options.getMainArm().getValue();
        return arm.getOpposite();
    }
}
