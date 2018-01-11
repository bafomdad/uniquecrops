package com.bafomdad.uniquecrops.events;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCDyePlantStitch;
import com.bafomdad.uniquecrops.core.UCInvisibiliaStitch;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCKeys;
import com.bafomdad.uniquecrops.network.PacketSendKey;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class UCEventHandlerClient {
	
	private static final String[] FIELD = new String[] { "mapRegisteredSprites", "field_110574_e", "bwd" };
	
	@SubscribeEvent
	public void loadTextures(TextureStitchEvent.Pre event) {
		
		try {
			Map<String, TextureAtlasSprite> mapRegisteredSprites = ReflectionHelper.getPrivateValue(TextureMap.class, event.getMap(), FIELD);
			for (int i = 1; i < 6; i++)
				mapRegisteredSprites.put("uniquecrops:blocks/invisibilia" + i, new UCInvisibiliaStitch("uniquecrops:blocks/invisibilia" + i));
			mapRegisteredSprites.put("uniquecrops:blocks/dyeplant5", new UCDyePlantStitch("uniquecrops:blocks/dyeplant5"));
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
	public void registerModels(ModelRegistryEvent event) {
		
		// BLOCKS
		registerBlockModel(UCBlocks.oldCobble);
		registerBlockModel(UCBlocks.oldCobbleMoss);
		registerBlockModel(UCBlocks.oldBrick);
		registerBlockModel(UCBlocks.oldGravel);
		registerBlockModel(UCBlocks.oldGrass);
		registerBlockModel(UCBlocks.hourglass);
		registerBlockModel(UCBlocks.totemhead);
		registerBlockModel(UCBlocks.lavalily);
		registerBlockModel(UCBlocks.icelily);
		registerBlockModel(UCBlocks.darkBlock);
		registerBlockModel(UCBlocks.barrel);
		
		// ITEMS
		for (Item item : UCItems.items)
			registerItemModel(item);
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
