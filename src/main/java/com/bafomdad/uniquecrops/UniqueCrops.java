package com.bafomdad.uniquecrops;

import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCTab;
import com.bafomdad.uniquecrops.core.UCWorldData;
import com.bafomdad.uniquecrops.data.DataGenerators;
import com.bafomdad.uniquecrops.events.UCEventHandlerCommon;
import com.bafomdad.uniquecrops.init.*;
import com.bafomdad.uniquecrops.integration.CuriosIntegration;
import com.bafomdad.uniquecrops.items.curios.EmblemScarab;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.bafomdad.uniquecrops.proxies.ClientProxy;
import com.bafomdad.uniquecrops.proxies.CommonProxy;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UniqueCrops.MOD_ID)
public class UniqueCrops {

    public static final String MOD_ID = "uniquecrops";

    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    public static final UCTab TAB = new UCTab();

    public UniqueCrops() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, UCConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UCConfig.COMMON_SPEC);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);
        UCSounds.SOUNDS.register(bus);
        UCItems.ITEMS.register(bus);
        UCBlocks.BLOCKS.register(bus);
        UCTiles.TILES.register(bus);
        UCScreens.CONTAINERS.register(bus);
        UCEntities.ENTITIES.register(bus);
        bus.addListener(UCEntities::registerAttributes);
        UCFeatures.FEATURE.register(bus);
        UCPotions.POTIONS.register(bus);
        UCRecipes.RECIPES.register(bus);
        bus.addListener(DataGenerators::gatherData);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(UCEventHandlerCommon::onBlockInteract);
        forgeBus.addListener(UCEventHandlerCommon::onBiomeLoading);
        forgeBus.addListener(UCEventHandlerCommon::updateAnvilCost);
        forgeBus.addListener(UCEventHandlerCommon::onBonemealEvent);
        forgeBus.addListener(UCEventHandlerCommon::jumpTele);
        forgeBus.addListener(UCEventHandlerCommon::addSeed);
        forgeBus.addListener(UCEventHandlerCommon::injectLoot);
        forgeBus.addListener(UCEventHandlerCommon::addTags);
        forgeBus.addListener(this::onServerStarting);
        forgeBus.addListener(this::registerCommands);
    }

    private void setup(final FMLCommonSetupEvent event) {

        CPProvider.register();
        UCFeatures.registerOre();
        UCRecipes.registerBrews();
        UCPacketHandler.init();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

        CuriosIntegration.sendImc(event);
    }

    private void processIMC(final InterModProcessEvent event) {

        event.getIMCStream().filter(msg -> msg.getMethod().equals(UCStrings.BLACKLIST_EFFECT) && msg.getMessageSupplier().get() instanceof String)
                .forEach(s -> {
                    String value = s.getMessageSupplier().get().toString();
                    EmblemScarab.blacklistPotionEffect(value);
                });
    }

    private void onServerStarting(FMLServerStartingEvent event) {

        UCWorldData.getInstance(World.OVERWORLD).markDirty();
    }

    private void registerCommands(RegisterCommandsEvent event) {

        UCCommands.register(event.getDispatcher());
    }
}
