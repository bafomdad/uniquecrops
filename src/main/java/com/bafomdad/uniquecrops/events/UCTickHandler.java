package com.bafomdad.uniquecrops.events;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UniqueCrops.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UCTickHandler {

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

        if (event.phase == TickEvent.Phase.END) {
            Screen gui = Minecraft.getInstance().currentScreen;
            if (gui == null || !gui.isPauseScreen()) {
                ticksInGame++;
                partialTicks = 0;
            }
            calcDelta();
        }
    }
}
