package com.bafomdad.uniquecrops.events;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCDyePlantStitch;
import com.bafomdad.uniquecrops.core.UCInvisibiliaStitch;
import com.bafomdad.uniquecrops.core.enums.*;
import com.bafomdad.uniquecrops.entities.EntityBattleCrop;
import com.bafomdad.uniquecrops.entities.EntityItemCooking;
import com.bafomdad.uniquecrops.entities.EntityMirror;
import com.bafomdad.uniquecrops.entities.EntityMovingCrop;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCKeys;
import com.bafomdad.uniquecrops.init.UCSounds;
import com.bafomdad.uniquecrops.items.ItemGeneric;
import com.bafomdad.uniquecrops.network.PacketSendKey;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.bafomdad.uniquecrops.render.ClientMethodHandles;
import com.bafomdad.uniquecrops.render.UCParticleSpawner;
import com.bafomdad.uniquecrops.render.entity.RenderBattleCropEntity;
import com.bafomdad.uniquecrops.render.entity.RenderCookingItem;
import com.bafomdad.uniquecrops.render.entity.RenderMirrorEntity;
import com.bafomdad.uniquecrops.render.entity.RenderMovingCrop;

public class UCEventHandlerClient {
	
	private static final String[] FIELD = new String[] { "mapRegisteredSprites", "field_110574_e", "bwd" };

	@SubscribeEvent
	public void loadTextures(TextureStitchEvent.Pre event) {
		
		try {
			Map<String, TextureAtlasSprite> mapRegisteredSprites = ReflectionHelper.getPrivateValue(TextureMap.class, event.getMap(), FIELD);
			for (int i = 1; i < 6; i++)
				mapRegisteredSprites.put("uniquecrops:blocks/invisibilia" + i, new UCInvisibiliaStitch("uniquecrops:blocks/invisibilia" + i));
			mapRegisteredSprites.put("uniquecrops:blocks/dyeplant5", new UCDyePlantStitch("uniquecrops:blocks/dyeplant5"));
			mapRegisteredSprites.put("uniquecrops:blocks/invisiglass", new UCInvisibiliaStitch("uniquecrops:blocks/invisiglass"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public void handleKeyPressed(InputEvent.KeyInputEvent event) {
		
		if (UCKeys.pixelKey.isPressed()) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			if (player instanceof FakePlayer) return;
			if (player.inventory.armorItemInSlot(3).getItem() != UCItems.pixelGlasses) return;
			
			UCPacketHandler.INSTANCE.sendToServer(new PacketSendKey());
		}
	}
	
    @SubscribeEvent
    public void showTooltip(ItemTooltipEvent event) {
    	
    	if (event.getItemStack().getItem() != Item.getItemFromBlock(Blocks.MOB_SPAWNER)) return;
    	
    	ItemStack tooltipper = event.getItemStack();
    	if (!tooltipper.hasTagCompound() || (tooltipper.hasTagCompound() && !tooltipper.getTagCompound().hasKey("Spawner"))) return;
    	
    	NBTTagCompound tag = tooltipper.getTagCompound().getCompoundTag("Spawner");
    	event.getToolTip().add("Mob spawner data:");
    	event.getToolTip().add(TextFormatting.GOLD + tag.getCompoundTag("SpawnData").getString("id"));
    }
    
    @SubscribeEvent
    public void renderWorldLast(RenderWorldLastEvent event) {
    	
    	Minecraft mc = Minecraft.getMinecraft();
    	Profiler profiler = mc.profiler;
    	
    	profiler.startSection("uniquecrops-particles");
    	UCParticleSpawner.dispatch();
    	profiler.endSection();

    	EntityPlayer player = mc.player;
    	ItemStack glasses = player.inventory.armorItemInSlot(3);
    	if (glasses.getItem() == UCItems.pixelGlasses) {
    		boolean flag = NBTUtils.getBoolean(glasses, "isActive", false);
    		boolean flag2 = ((IBookUpgradeable)UCItems.pixelGlasses).isMaxLevel(glasses);
    		if (flag && flag2) {
        		GlStateManager.pushMatrix();
        		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        		GlStateManager.disableDepth();
        		GlStateManager.enableBlend();
        		
        		BlockPos pos = BlockPos.fromLong(NBTUtils.getLong(glasses, "orePos", BlockPos.ORIGIN.toLong()));
        		if (!pos.equals(BlockPos.ORIGIN)) {
        			player.getEntityData().setLong("orePos", pos.toLong());
        			renderOres(pos, mc);
        		}
        		GlStateManager.enableDepth();
        		GlStateManager.disableBlend();
        		GL11.glPopAttrib();
        		GlStateManager.popMatrix();
    		}
    	}
    }
    
    private void renderOres(BlockPos pos, Minecraft mc) {

    	double renderPosX, renderPosY, renderPosZ;
    	
    	try {
    		renderPosX = (double)ClientMethodHandles.renderPosX_getter.invokeExact(mc.getRenderManager());
    		renderPosY = (double)ClientMethodHandles.renderPosY_getter.invokeExact(mc.getRenderManager());
    		renderPosZ = (double)ClientMethodHandles.renderPosZ_getter.invokeExact(mc.getRenderManager());
    	} catch (Throwable t) {
    		return;
    	}
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.translate(0, 0, 1);
    	GlStateManager.translate(pos.getX() - renderPosX, pos.getY() - renderPosY, pos.getZ() - renderPosZ);
    	
    	mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    	BlockRendererDispatcher brd = mc.getBlockRendererDispatcher();
    	brd.renderBlockBrightness(Blocks.PINK_GLAZED_TERRACOTTA.getDefaultState(), 1.0F);
    	
    	GlStateManager.popMatrix();
    }
    
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    	
    	if (event.getModID().equals(UniqueCrops.MOD_ID)) {
    		ConfigManager.sync(UniqueCrops.MOD_ID, Config.Type.INSTANCE);
    		UCConfig.syncMultiblocks();
    	}
    }
    
    @SubscribeEvent
    public void registerColors(ColorHandlerEvent.Item event) {
    	
    	ItemColors ic = event.getItemColors();
    	ic.registerItemColorHandler(new IItemColor() {	
    		
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				
				if (!stack.isEmpty()) {
					if ((stack.getItem() instanceof ItemGeneric && stack.getItemDamage() == EnumItems.POTIONSPLASH.ordinal()) || stack.getItem() == UCItems.potionReverse) {
						if (tintIndex == 0)
							return 0x845c28;
					}
					if (stack.getItem() == UCItems.potionEnnui) {
						if (tintIndex == 0)
							return 0x1C062D;
					}
				}
				return 0xffffff;
			}
		}, UCItems.generic, UCItems.potionReverse, UCItems.potionEnnui);
    	ic.registerItemColorHandler(new IItemColor() {
    		
    		@Override
    		public int colorMultiplier(ItemStack stack, int tintIndex) {
    			
    			return EnumDyeColor.byDyeDamage(stack.getItemDamage()).getColorValue();
    		}
    	}, UCItems.dyedBonemeal);
    }
    
    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent event) {
    	
    	if (event.getSound() == null || event.getSound().getCategory() == null) return;
    	
    	if (event.getSound().getSoundLocation().getPath().equals("entity.player.hurt")) {
    		Minecraft mc = Minecraft.getMinecraft();
    		if (mc.player != null) {
    			boolean flag = mc.player.inventory.hasItemStack(new ItemStack(UCBlocks.hourglass));
    			if (flag) {
	        		event.setResultSound(null);
	        		mc.world.playSound(mc.player, mc.player.getPosition(), UCSounds.OOF, SoundCategory.PLAYERS, 1F, 1F);
    			}
    		}
    	}
    }
    
//    @SubscribeEvent :)
    public void onBlockOverlay(RenderBlockOverlayEvent event) {
    	
    	if (event.getOverlayType() == OverlayType.BLOCK) {
    		event.setCanceled(true);
    	}
    }
    
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		
		// BLOCKS
		registerBlockModel(UCBlocks.oldCobble);
		registerBlockModel(UCBlocks.oldCobbleMoss);
		registerBlockModel(UCBlocks.oldBrick);
		registerBlockModel(UCBlocks.oldGravel);
		registerBlockModel(UCBlocks.oldGrass);
		registerBlockModel(UCBlocks.hourglass, "itemhourglass");
		registerBlockModel(UCBlocks.totemHead, "itemtotemhead");
		registerBlockModel(UCBlocks.lavaLily);
		registerBlockModel(UCBlocks.iceLily);
		registerBlockModel(UCBlocks.jungleLily);
		registerBlockModel(UCBlocks.teleLily);
		registerBlockModel(UCBlocks.darkBlock);
		registerBlockModel(UCBlocks.barrel, "itemabstractbarrel");
		registerBlockModel(UCBlocks.invisiGlass);
		registerBlockModel(UCBlocks.demoCord);
		registerBlockModel(UCBlocks.mirror);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UCBlocks.goblet), 0, new ModelResourceLocation(UniqueCrops.MOD_ID + ":goblet_empty", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UCBlocks.goblet), 1, new ModelResourceLocation(UniqueCrops.MOD_ID + ":goblet_filled", "inventory"));
		registerBlockModel(UCBlocks.sunBlock);
		registerBlockModel(UCBlocks.driedThatch);
		registerBlockModel(UCBlocks.normieCrate);
		registerBlockModel(UCBlocks.ruinedBricks);
		registerBlockModel(UCBlocks.ruinedBricksCarved);
		registerBlockModel(UCBlocks.ruinedBricksRed);
		registerBlockModel(UCBlocks.ruinedBricksGhost);
		registerBlockModel(UCBlocks.portal);
		registerBlockModel(UCBlocks.cocito);
		registerBlockModel(UCBlocks.harvestTrap, "itemharvesttrap");
		registerBlockModel(UCBlocks.bucketRope, "itembucketrope");
		registerBlockModel(UCBlocks.flywoodLog);
		registerCustomBlockModel(UCBlocks.flywoodLeaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		registerBlockModel(UCBlocks.flywoodSapling);
		registerBlockModel(UCBlocks.flywoodPlank);
		registerBlockModel(UCBlocks.eggBasket);
		registerBlockModel(UCBlocks.precisionBlock);
		registerBlockModel(UCBlocks.cinderTorch);
		
		// ITEMS
		for (Item item : UCItems.items)
			registerItemModel(item);
		ModelLoader.setCustomModelResourceLocation(UCItems.impregnatedLeather, 1, new ModelResourceLocation(UCItems.impregnatedLeather.getRegistryName(), "inventory"));

		// ENTITIES
		RenderingRegistry.registerEntityRenderingHandler(EntityMirror.class, RenderMirrorEntity.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleCrop.class, RenderBattleCropEntity.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityItemCooking.class, RenderCookingItem.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingCrop.class, RenderMovingCrop.FACTORY);
	}
	
	private void registerBlockModel(Block block) {
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	private void registerBlockModel(Block block, String resLoc) {
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(UniqueCrops.MOD_ID + ":" + resLoc, "inventory"));
	}
	
	private void registerCustomBlockModel(Block block, IProperty... propertyToIgnore) {
		
		IStateMapper stateMapper = new StateMap.Builder().ignore(propertyToIgnore).build();
		ModelLoader.setCustomStateMapper(block, stateMapper);
		ModelResourceLocation mrl = stateMapper.putStateModelLocations(block).get(block.getDefaultState());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, mrl);
	}
	
	private void registerItemModel(Item item) {
		
		if (item == UCItems.generic) {
			for (int i = 0; i < EnumItems.values().length; i++)
				ModelLoader.setCustomModelResourceLocation(UCItems.generic, i, new ModelResourceLocation(UCItems.generic.getRegistryName() + "." + EnumItems.values()[i].getName(), "inventory"));
		}
		if (item == UCItems.dyedBonemeal) {
			for (int i = 0; i < EnumDyeColor.values().length; i++)
				ModelLoader.setCustomModelResourceLocation(UCItems.dyedBonemeal, i, new ModelResourceLocation(UCItems.dyedBonemeal.getRegistryName(), "inventory"));
		}
		else if (item != UCItems.generic)
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
