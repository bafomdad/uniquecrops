package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.DyeUtils;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.gui.*;
import com.bafomdad.uniquecrops.render.entity.*;
import com.bafomdad.uniquecrops.render.model.*;
import com.bafomdad.uniquecrops.render.particle.SparkFX;
import com.bafomdad.uniquecrops.render.tile.*;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = UniqueCrops.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UCClient {

    public static KeyMapping PIXEL_GLASSES;

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {

        registerRenderTypes();
        registerScreens();
        registerKeys();
        event.enqueueWork(UCClient::registerColorHandler);
        event.enqueueWork(UCClient::registerPropertyGetters);
    }

    private static void registerRenderTypes() {

        for (Block block : UCBlocks.CROPS)
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(UCBlocks.INVISIBILIA_GLASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.HOURGLASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.FLYWOOD_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.LILY_ENDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.LILY_ICE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.LILY_JUNGLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.LILY_LAVA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.SUN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.DEMO_CORD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.ITERO.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.SANALIGHT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.FLYWOOD_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.ROSEWOOD_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UCBlocks.DREAMCATCHER.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(UCBlocks.CROP_PORTAL.get(), RenderType.translucent());
    }

    private static void registerScreens() {

        MenuScreens.register(UCScreens.BARREL.get(), GuiBarrel::new);
        MenuScreens.register(UCScreens.CRAFTYPLANT.get(), GuiCraftyPlant::new);
    }

    private static void registerKeys() {

        PIXEL_GLASSES = new KeyMapping("key.uniquecrops.pixelglasses", KeyConflictContext.IN_GAME, InputConstants.getKey(GLFW.GLFW_KEY_V, 0), "key.uc.pixelglasses");
        ClientRegistry.registerKeyBinding(UCClient.PIXEL_GLASSES);
    }

    private static void registerPropertyGetters() {

        registerPropertyGetter(UCItems.DIAMONDS.get(), new ResourceLocation(UniqueCrops.MOD_ID, "diamonds"),
                (stack, world, entity, seed) -> NBTUtils.getInt(stack, UCStrings.TAG_DIAMONDS, 0));
        registerPropertyGetter(UCItems.IMPACT_SHIELD.get(), new ResourceLocation(UniqueCrops.MOD_ID, "blocking"),
                (stack, world, entity, seed) -> (entity != null && entity.getUseItem() == stack) ? 1.0F : 0.0F);
    }

    private static void registerPropertyGetter(ItemLike item, ResourceLocation id, @SuppressWarnings("deprecation")ItemPropertyFunction prop) {

        ItemProperties.register(item.asItem(), id, prop);
    }

    private static void registerColorHandler() {

        ItemColors ic = Minecraft.getInstance().getItemColors();
        ic.register((stack, tintIndex) -> {

            if (tintIndex == 0) {
                if (stack.getItem() == UCItems.POTION_REVERSE.get())
                    return 0x845c28;
                if (stack.getItem() == UCItems.POTION_ENNUI.get())
                    return 0xeef442;
                if (stack.getItem() == UCItems.POTION_IGNORANCE.get())
                    return 0x00ccff;
                if (stack.getItem() == UCItems.POTION_ZOMBIFICATION.get())
                    return 0x93C47D;
            }
            return 0xffffff;
        }, UCItems.POTION_ENNUI.get(), UCItems.POTION_IGNORANCE.get(), UCItems.POTION_REVERSE.get(), UCItems.POTION_ZOMBIFICATION.get());

        DyeUtils.BONEMEAL_DYE.forEach((key, value) -> ic.register((stack, tintIndex) -> tintIndex == 0 ? key.getMaterialColor().col : -1, value.asItem()));
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerBlockEntityRenderer(UCTiles.ARTISIA.get(), RenderItemTile.Artisia::new);
        event.registerBlockEntityRenderer(UCTiles.WEATHERFLESIA.get(), RenderItemTile.Weatherflesia::new);
        event.registerBlockEntityRenderer(UCTiles.LACUSIA.get(), RenderItemTile.Lacusia::new);
        event.registerBlockEntityRenderer(UCTiles.SUNTILE.get(), RenderSunBlock::new);
        event.registerBlockEntityRenderer(UCTiles.SUNDIAL.get(), RenderSundial::new);
        event.registerBlockEntityRenderer(UCTiles.FASCINO.get(), RenderFascino::new);
        event.registerBlockEntityRenderer(UCTiles.ITERO.get(), RenderItero::new);
        event.registerBlockEntityRenderer(UCTiles.EXEDO.get(), RenderExedo::new);
        event.registerBlockEntityRenderer(UCTiles.SUCCO.get(), RenderSucco::new);

        event.registerEntityRenderer(UCEntities.BATTLE_CROP.get(), RenderBattleCropEntity::new);
        event.registerEntityRenderer(UCEntities.MOVING_CROP.get(), RenderNone::new);
        event.registerEntityRenderer(UCEntities.WEEPING_EYE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UCEntities.THROWABLE_BOOK.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(ModelBattleCrop.LAYER_LOCATION, () -> LayerDefinition.create(ModelBattleCrop.createBodyLayer(), 32, 32));
        event.registerLayerDefinition(ModelCubeyThingy.LAYER_LOCATION, () -> LayerDefinition.create(ModelCubeyThingy.createBodyLayer(), 16, 16));
        event.registerLayerDefinition(ModelExedo.LAYER_LOCATION, () -> LayerDefinition.create(ModelExedo.createBodyLayer(), 64, 64));
        event.registerLayerDefinition(ModelSundial.LAYER_LOCATION, () -> LayerDefinition.create(ModelSundial.createBodyLayer(), 64, 32));
    }

    @SubscribeEvent
    public static void registerExtraLayers(EntityRenderersEvent.AddLayers event) {

        event.getSkins().forEach(s -> {
            if (event.getSkin(s) instanceof PlayerRenderer renderer)
                renderer.addLayer(new RenderLayerPants(renderer));
        });
    }

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent event) {

        Minecraft.getInstance().particleEngine.register(UCParticles.SPARK.get(), SparkFX.Factory::new);
    }
}
