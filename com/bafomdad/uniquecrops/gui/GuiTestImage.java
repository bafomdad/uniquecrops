package com.bafomdad.uniquecrops.gui;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.glu.Project;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

public class GuiTestImage extends Page {
	
	final Block block;		
	final GuiBookGuide screen;
	
	private static class FakeBlockAccess implements IBlockAccess {
		
		private final GuiTestImage gui;
		private IBlockState state = Blocks.AIR.getDefaultState();
		
		public FakeBlockAccess(GuiTestImage gui) {
			
			this.gui = gui;
		}
		
		public void setState(IBlockState state) {
			
			this.state = state;
		}

		@Override
		public TileEntity getTileEntity(BlockPos pos) {
	
			return null;
		}

		@Override
		public int getCombinedLight(BlockPos pos, int lightValue) {

			return 0xF000F0;
		}

		@Override
		public IBlockState getBlockState(BlockPos pos) {

			return state;
		}

		@Override
		public boolean isAirBlock(BlockPos pos) {

			return getBlockState(pos).getBlock() == Blocks.AIR;
		}

		@Override
		public Biome getBiome(BlockPos pos) {

			return Biomes.PLAINS;
		}

		@Override
		public int getStrongPower(BlockPos pos, EnumFacing direction) {

			return 0;
		}

		@Override
		public WorldType getWorldType() {

			return WorldType.DEFAULT;
		}

		@Override
		public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {

			return getBlockState(pos).isSideSolid(this, pos, side);
		} 
	}
	
	FakeBlockAccess fakeWorld = new FakeBlockAccess(this);

	public GuiTestImage(GuiBookGuide screen, Block block) {
		
		super(screen);
		this.screen = screen;
		this.block = block;
	}
	
	@Override
	public void draw() {
		
		super.draw();
		
		mc.fontRenderer.drawString("hi :]", this.drawX, this.drawY, Color.BLACK.getRGB());
		
		GlStateManager.pushMatrix();
		int k = (gui.width - gui.WIDTH) / 2;
		int j = (gui.height - gui.HEIGHT) / 2;
		GlStateManager.translate(k + 90, j + 60, screen.getZLevel() + 100F);
		
		GlStateManager.scale(50, 50, 50);
		GlStateManager.rotate(145F, 1, 0, 0);
		GlStateManager.rotate(20F, 0, 1, 0);
		
		GlStateManager.translate(-0.5, -0.5, -0.5);
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		IBlockState state = block.getDefaultState().withProperty(BlockCrops.AGE, 7);
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		BlockRendererDispatcher brd = mc.getBlockRendererDispatcher();
		GlStateManager.translate(0, 0, 0 + 1);
		GlStateManager.color(1, 1, 1, 1);
		brd.renderBlockBrightness(state, 1.0F);
		
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
		
		GlStateManager.popMatrix();
	}
}
