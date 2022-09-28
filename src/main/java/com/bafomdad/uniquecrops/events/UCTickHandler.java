package com.bafomdad.uniquecrops.events;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UniqueCrops.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UCTickHandler {

    public static final ResourceLocation BITS = new ResourceLocation("minecraft", "shaders/post/bits.json");

    private UCTickHandler() {}

    public static int ticksInGame = 0;
    public static float partialTicks = 0;
    public static float delta = 0;
    public static float total = 0;

    private static void calcDelta() {

        float oldTotal = total;
        total = ticksInGame + partialTicks;
        delta = total - oldTotal;
    }

    @SubscribeEvent
    public static void renderTick(TickEvent.RenderTickEvent event) {

        if (event.phase == TickEvent.Phase.START)
            partialTicks = event.renderTickTime;
        else
            calcDelta();
    }

    @SubscribeEvent
    public static void clientTickEnd(TickEvent.ClientTickEvent event) {

        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END) {
            Screen gui = mc.screen;
            if (gui == null || !gui.isPauseScreen()) {
                ticksInGame++;
                partialTicks = 0;
            }
            calcDelta();
        }
        Player player = mc.player;
        if (mc.level == null || player == null) return;

        ItemStack glasses = player.getInventory().getArmor(3);
        if (glasses.getItem() == UCItems.GLASSES_PIXELS.get()) {
            boolean flag = NBTUtils.getBoolean(glasses, "isActive", false);
            if (flag)
                mc.gameRenderer.loadEffect(BITS);
            else if (!flag && mc.gameRenderer.currentEffect() != null && mc.gameRenderer.currentEffect().getName().equals(BITS.toString()))
                mc.gameRenderer.shutdownEffect();
        }
        else if (mc.gameRenderer.currentEffect() != null && mc.gameRenderer.currentEffect().getName().equals(BITS.toString()))
            mc.gameRenderer.shutdownEffect();
    }
}
