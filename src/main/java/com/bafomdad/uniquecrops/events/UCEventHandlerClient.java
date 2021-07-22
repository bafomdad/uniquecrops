package com.bafomdad.uniquecrops.events;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.gui.GuiStaffOverlay;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCClient;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCSounds;
import com.bafomdad.uniquecrops.network.PacketSendKey;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UniqueCrops.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UCEventHandlerClient {

    public static final ResourceLocation BITS = new ResourceLocation("minecraft", "shaders/post/bits.json");

    @SubscribeEvent
    public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {

        Minecraft mc = Minecraft.getInstance();
        GuiStaffOverlay overlay = new GuiStaffOverlay(mc);
        overlay.renderOverlay(event);
    }

    @SubscribeEvent
    public static void handleKeyPressed(InputEvent.KeyInputEvent event) {

        if (UCClient.PIXEL_GLASSES.isPressed()) {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null) return;
            if (player.inventory.armorItemInSlot(3).getItem() != UCItems.GLASSES_PIXELS.get()) return;

            UCPacketHandler.INSTANCE.sendToServer(new PacketSendKey());
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {

        if (event.getSound() == null || event.getSound().getCategory() == null) return;

        if (event.getSound().getSoundLocation().getPath().equals("entity.player.hurt")) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                boolean flag = mc.player.inventory.hasItemStack(new ItemStack(UCBlocks.HOURGLASS.get()));
                if (flag) {
                    event.setResultSound(null);
                    mc.world.playSound(mc.player, mc.player.getPosition(), UCSounds.OOF.get(), SoundCategory.PLAYERS, 1F, 1F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void showTooltip(ItemTooltipEvent event) {

        if (event.getItemStack().getItem() != Blocks.SPAWNER.asItem()) return;

        ItemStack tooltipper = event.getItemStack();
        if (!tooltipper.hasTag() || (tooltipper.hasTag() && !tooltipper.getTag().contains("Spawner"))) return;

        CompoundNBT tag = tooltipper.getTag().getCompound("Spawner");
        event.getToolTip().add(new StringTextComponent("Mob spawner data:"));
        event.getToolTip().add(new StringTextComponent(TextFormatting.GOLD + tag.getCompound("SpawnData").getString("id")));
    }

    @SubscribeEvent
    public static void renderWorldLast(RenderWorldLastEvent event) {

        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        ItemStack glasses = player.inventory.armorItemInSlot(3);
        if (glasses.getItem() == UCItems.GLASSES_PIXELS.get()) {
            boolean flag = NBTUtils.getBoolean(glasses, "isActive", false);
            boolean flag2 = ((IBookUpgradeable)glasses.getItem()).isMaxLevel(glasses);
            if (flag)
                mc.gameRenderer.loadShader(BITS);
            else if (!flag && mc.gameRenderer.getShaderGroup() != null && mc.gameRenderer.getShaderGroup().getShaderGroupName().equals(BITS.toString()))
                mc.gameRenderer.stopUseShader();
            if (flag && flag2) {
                MatrixStack ms = event.getMatrixStack();
                ms.push();
                RenderSystem.pushLightingAttributes();
                RenderSystem.disableDepthTest();
                RenderSystem.disableBlend();

                BlockPos pos = BlockPos.fromLong(NBTUtils.getLong(glasses, "orePos", BlockPos.ZERO.toLong()));
                if (!pos.equals(BlockPos.ZERO)) {
                    renderOres(pos, mc, ms);
                }
                RenderSystem.enableDepthTest();
                RenderSystem.disableBlend();
                RenderSystem.popAttributes();
                ms.pop();
            }
        }
        else if (mc.gameRenderer.getShaderGroup() != null && mc.gameRenderer.getShaderGroup().getShaderGroupName().equals(BITS.toString()))
            mc.gameRenderer.stopUseShader();
    }

    private static void renderOres(BlockPos pos, Minecraft mc, MatrixStack ms) {

        double renderPosX = mc.getRenderManager().info.getProjectedView().x;
        double renderPosY = mc.getRenderManager().info.getProjectedView().y;
        double renderPosZ = mc.getRenderManager().info.getProjectedView().z;

        ms.push();
        ms.translate(pos.getX() - renderPosX, pos.getY() - renderPosY, pos.getZ() - renderPosZ);

        mc.textureManager.bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
        BlockRendererDispatcher brd = mc.getBlockRendererDispatcher();
        IRenderTypeBuffer.Impl renderBuffer = mc.getRenderTypeBuffers().getBufferSource();
        brd.renderBlock(Blocks.PINK_GLAZED_TERRACOTTA.getDefaultState(), ms, renderBuffer, 0xF000F0, OverlayTexture.NO_OVERLAY);
        ms.pop();
    }
}
