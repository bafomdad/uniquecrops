package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.DyeUtils;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.gui.*;
import com.bafomdad.uniquecrops.render.entity.RenderBattleCropEntity;
import com.bafomdad.uniquecrops.render.entity.RenderLayerPants;
import com.bafomdad.uniquecrops.render.entity.RenderNone;
import com.bafomdad.uniquecrops.render.tile.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

@Mod.EventBusSubscriber(modid = UniqueCrops.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UCClient {

    public static KeyBinding PIXEL_GLASSES;

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {

        registerRenderTypes();
        registerTileRenderers();
        registerScreens();
        registerEntityRenderer();
        registerLayerRenderer();
        registerKeys();
        event.enqueueWork(UCClient::registerColorHandler);
        event.enqueueWork(UCClient::registerPropertyGetters);
    }



    private static void registerRenderTypes() {

        for (Block block : UCBlocks.CROPS)
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(UCBlocks.INVISIBILIA_GLASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.HOURGLASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.FLYWOOD_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.LILY_ENDER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.LILY_ICE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.LILY_JUNGLE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.LILY_LAVA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.SUN_BLOCK.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.DEMO_CORD.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(UCBlocks.ITERO.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(UCBlocks.CROP_PORTAL.get(), RenderType.getTranslucent());
    }

    private static void registerTileRenderers() {

        ClientRegistry.bindTileEntityRenderer(UCTiles.ARTISIA.get(), RenderItemTile.Artisia::new);
        ClientRegistry.bindTileEntityRenderer(UCTiles.WEATHERFLESIA.get(), RenderItemTile.Weatherflesia::new);
        ClientRegistry.bindTileEntityRenderer(UCTiles.LACUSIA.get(), RenderItemTile.Lacusia::new);
        ClientRegistry.bindTileEntityRenderer(UCTiles.SUNTILE.get(), RenderSunBlock::new);
        ClientRegistry.bindTileEntityRenderer(UCTiles.SUNDIAL.get(), RenderSundial::new);
        ClientRegistry.bindTileEntityRenderer(UCTiles.FASCINO.get(), RenderFascino::new);
        ClientRegistry.bindTileEntityRenderer(UCTiles.ITERO.get(), RenderItero::new);
        ClientRegistry.bindTileEntityRenderer(UCTiles.EXEDO.get(), RenderExedo::new);
        ClientRegistry.bindTileEntityRenderer(UCTiles.SUCCO.get(), RenderSucco::new);
    }

    private static void registerScreens() {

        ScreenManager.registerFactory(UCScreens.BARREL.get(), GuiBarrel::new);
        ScreenManager.registerFactory(UCScreens.CRAFTYPLANT.get(), GuiCraftyPlant::new);
    }

    private static void registerEntityRenderer() {

        RenderingRegistry.registerEntityRenderingHandler(UCEntities.BATTLE_CROP.get(), RenderBattleCropEntity::new);
        RenderingRegistry.registerEntityRenderingHandler(UCEntities.MOVING_CROP.get(), RenderNone::new);

        RenderingRegistry.registerEntityRenderingHandler(UCEntities.WEEPING_EYE.get(), renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(UCEntities.THROWABLE_BOOK.get(), renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
    }

    private static void registerLayerRenderer() {

        Map<String, PlayerRenderer> skinMap = Minecraft.getInstance().getRenderManager().getSkinMap();
        PlayerRenderer render;
        render = skinMap.get("default");
        render.addLayer(new RenderLayerPants(render));

        render = skinMap.get("slim");
        render.addLayer(new RenderLayerPants(render));
    }

    private static void registerKeys() {

        PIXEL_GLASSES = new KeyBinding("key.uniquecrops.pixelglasses", KeyConflictContext.IN_GAME, InputMappings.getInputByCode(GLFW.GLFW_KEY_V, 0), "Unique Crops");
        ClientRegistry.registerKeyBinding(UCClient.PIXEL_GLASSES);
    }

    private static void registerPropertyGetters() {

        registerPropertyGetter(UCItems.DIAMONDS.get(), new ResourceLocation(UniqueCrops.MOD_ID, "diamonds"),
                (stack, world, entity) -> NBTUtils.getInt(stack, UCStrings.TAG_DIAMONDS, 0));
        registerPropertyGetter(UCItems.IMPACT_SHIELD.get(), new ResourceLocation(UniqueCrops.MOD_ID, "blocking"),
                (stack, world, entity) -> (entity != null && entity.getActiveItemStack() == stack) ? 1.0F : 0.0F);
    }

    private static void registerPropertyGetter(IItemProvider item, ResourceLocation id, IItemPropertyGetter prop) {

        ItemModelsProperties.registerProperty(item.asItem(), id, prop);
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
            }
            return 0xffffff;
        }, UCItems.POTION_ENNUI.get(), UCItems.POTION_IGNORANCE.get(), UCItems.POTION_REVERSE.get());

        DyeUtils.BONEMEAL_DYE.forEach((key, value) -> ic.register((stack, tintIndex) -> tintIndex == 0 ? key.getColorValue() : -1, value.asItem()));
    }
}
