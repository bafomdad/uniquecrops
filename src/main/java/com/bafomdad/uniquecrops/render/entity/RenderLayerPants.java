package com.bafomdad.uniquecrops.render.entity;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ThunderpantzItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderLayerPants extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

    public RenderLayerPants(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> renderer) {

        super(renderer);
    }

    @Override
    public void render(MatrixStack ms, IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch) {

        if (player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == UCItems.THUNDERPANTZ.get()) {
            ItemStack pants = player.getItemBySlot(EquipmentSlotType.LEGS);
            ms.pushPose();
            float f = (float)player.tickCount + partialTicks;
            float toScale = ((ThunderpantzItem)pants.getItem()).getCharge(pants) / 16.0F;
            IVertexBuilder builder = buffer.getBuffer(RenderType.energySwirl(LIGHTNING_TEXTURE, f * 0.01F, f * 0.01F));
            ms.translate(0, -0.25, 0);
            ms.scale(toScale, 1.0F, toScale);
            getParentModel().rightPants.render(ms, builder, light, OverlayTexture.NO_OVERLAY);
            getParentModel().leftPants.render(ms, builder, light, OverlayTexture.NO_OVERLAY);
            ms.popPose();
        }
    }
}
