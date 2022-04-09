package clientmods.effectanimation.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import clientmods.effectanimation.Effectanimation;

@Mixin(ClientPlayNetworkHandler.class)
public class EffectMixin {
    boolean omen;
    boolean hero;
    MinecraftClient client = MinecraftClient.getInstance();

    // This code looks so awkward because getEffectID is broken, so I xor the results to check if anything is added
    @Inject(at = @At("HEAD"), method = "onEntityStatusEffect")
    private void check_effects(EntityStatusEffectS2CPacket packet, CallbackInfo ci) {
        omen = client.player.hasStatusEffect(StatusEffects.BAD_OMEN);
        hero = client.player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
    }

    @Inject(at = @At("TAIL"), method = "onEntityStatusEffect")
    private void animate(EntityStatusEffectS2CPacket packet, CallbackInfo ci) {
        Entity entity = ((ClientPlayNetworkHandler)(Object)this).getWorld().getEntityById(packet.getEntityId());
        int duration = packet.getDuration();
        if (entity != client.player && duration > 0) {
            return;
        }
        if (client.player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE) ^ hero) {
            client.gameRenderer.showFloatingItem(new ItemStack(Effectanimation.HERO_OF_THE_VILLAGE));
        } else if (client.player.hasStatusEffect(StatusEffects.BAD_OMEN) ^ omen) {
            client.gameRenderer.showFloatingItem(new ItemStack(Effectanimation.BAD_OMEN));
        }
    }
}
