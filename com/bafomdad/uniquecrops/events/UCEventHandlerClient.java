package com.bafomdad.uniquecrops.events;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
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

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCDyePlantStitch;
import com.bafomdad.uniquecrops.core.UCInvisibiliaStitch;
import com.bafomdad.uniquecrops.entities.EntityBattleCrop;
import com.bafomdad.uniquecrops.entities.EntityMirror;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCKeys;
import com.bafomdad.uniquecrops.init.UCSounds;
import com.bafomdad.uniquecrops.items.ItemGeneric;
import com.bafomdad.uniquecrops.network.PacketSendKey;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.bafomdad.uniquecrops.render.RenderBattleCropEntity;
import com.bafomdad.uniquecrops.render.RenderMirrorEntity;

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
			if (player.inventory.armorItemInSlot(3) == null) return;
			if (player.inventory.armorItemInSlot(3).getItem() != UCItems.pixelglasses) return;
			
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
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    	
    	if (event.getModID().equals(UniqueCrops.MOD_ID))
    		ConfigManager.load(UniqueCrops.MOD_ID, Config.Type.INSTANCE);
    }
    
    @SubscribeEvent
    public void registerColors(ColorHandlerEvent.Item event) {
    	
    	event.getItemColors().registerItemColorHandler(new IItemColor() {	
    		
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				
				if (!stack.isEmpty()) {
					if ((stack.getItem() instanceof ItemGeneric && stack.getItemDamage() == EnumItems.POTIONSPLASH.ordinal()) || stack.getItem() == UCItems.potionreverse) {
						if (tintIndex == 0)
							return 0x845c28;
					}
				}
				return 0xffffff;
			}
		}, UCItems.generic, UCItems.potionreverse);
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
    
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		
		// BLOCKS
		registerBlockModel(UCBlocks.oldCobble);
		registerBlockModel(UCBlocks.oldCobbleMoss);
		registerBlockModel(UCBlocks.oldBrick);
		registerBlockModel(UCBlocks.oldGravel);
		registerBlockModel(UCBlocks.oldGrass);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UCBlocks.hourglass), 0, new ModelResourceLocation(UniqueCrops.MOD_ID + ":itemhourglass", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UCBlocks.totemhead), 0, new ModelResourceLocation(UniqueCrops.MOD_ID + ":itemtotemhead", "inventory"));
		registerBlockModel(UCBlocks.lavalily);
		registerBlockModel(UCBlocks.icelily);
		registerBlockModel(UCBlocks.darkBlock);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UCBlocks.barrel), 0, new ModelResourceLocation(UniqueCrops.MOD_ID + ":itemabstractbarrel", "inventory"));
		registerBlockModel(UCBlocks.invisiGlass);
		registerBlockModel(UCBlocks.demoCord);
		registerBlockModel(UCBlocks.mirror);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UCBlocks.goblet), 0, new ModelResourceLocation(UniqueCrops.MOD_ID + ":goblet_empty", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UCBlocks.goblet), 1, new ModelResourceLocation(UniqueCrops.MOD_ID + ":goblet_filled", "inventory"));
		
		// ITEMS
		for (Item item : UCItems.items)
			registerItemModel(item);
		
		// ENTITIES
		RenderingRegistry.registerEntityRenderingHandler(EntityMirror.class, RenderMirrorEntity.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleCrop.class, RenderBattleCropEntity.FACTORY);
	}
	
	private void registerBlockModel(Block block) {
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	private void registerItemModel(Item item) {
		
		if (item == UCItems.generic) {
			for (int i = 0; i < EnumItems.values().length; i++)
				ModelLoader.setCustomModelResourceLocation(UCItems.generic, i, new ModelResourceLocation(UCItems.generic.getRegistryName() + "." + EnumItems.values()[i].getName(), "inventory"));
		}
		else if (item != UCItems.generic)
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
