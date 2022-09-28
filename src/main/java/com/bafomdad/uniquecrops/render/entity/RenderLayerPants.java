package com.bafomdad.uniquecrops.render.entity;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ThunderpantzItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class RenderLayerPants extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

    public RenderLayerPants(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {

        super(renderer);
    }

    @Override
    public void render(PoseStack ms, MultiBufferSource buffer, int light, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch) {

        if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == UCItems.THUNDERPANTZ.get()) {
            ItemStack pants = player.getItemBySlot(EquipmentSlot.LEGS);
            ms.pushPose();
            float f = (float)player.tickCount + partialTicks;
            float toScale = ((ThunderpantzItem)pants.getItem()).getCharge(pants) / 16.0F;
            VertexConsumer builder = buffer.getBuffer(RenderType.energySwirl(LIGHTNING_TEXTURE, f * 0.01F, f * 0.01F));
            ms.translate(0, -0.25, 0);
            ms.scale(toScale, 1.0F, toScale);
            getParentModel().rightPants.render(ms, builder, light, OverlayTexture.NO_OVERLAY);
            getParentModel().leftPants.render(ms, builder, light, OverlayTexture.NO_OVERLAY);
            ms.popPose();
        }
    }
}
