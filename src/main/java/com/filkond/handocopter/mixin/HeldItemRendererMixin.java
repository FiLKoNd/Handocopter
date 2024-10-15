package com.filkond.handocopter.mixin;

import com.filkond.handocopter.HandocopterClient;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @ModifyVariable(method = "renderFirstPersonItem", at = @At(value = "STORE"))
    private Arm modifyArm(Arm original, @Local(ordinal = 0) boolean isMain) {
        if (!HandocopterClient.CONFIG.isEnabled) return original;
        Arm arm = MinecraftClient.getInstance().options.getMainArm().getValue();
        return isMain ? arm : arm.getOpposite();
    }
}
