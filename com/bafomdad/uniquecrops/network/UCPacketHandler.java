package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class UCPacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(UniqueCrops.MOD_ID);
	static int packetId = 0;
	
	public static void init() {
		
		INSTANCE.registerMessage(PacketSendKey.Handler.class, PacketSendKey.class, packetId++, Side.SERVER);
		INSTANCE.registerMessage(PacketUCEffect.Handler.class, PacketUCEffect.class, packetId++, Side.CLIENT);
		INSTANCE.registerMessage(PacketBookOpen.Handler.class, PacketBookOpen.class, packetId++, Side.CLIENT);
		INSTANCE.registerMessage(PacketSyncCap.Handler.class, PacketSyncCap.class, packetId++, Side.CLIENT);
		INSTANCE.registerMessage(PacketBiomeChange.Handler.class, PacketBiomeChange.class, packetId++, Side.CLIENT);
		INSTANCE.registerMessage(PacketColorfulCube.Handler.class, PacketColorfulCube.class, packetId++, Side.SERVER);
	}
	
	public static void sendToNearbyPlayers(World world, BlockPos pos, IMessage toSend) {
		
		if (world instanceof WorldServer) {
			WorldServer ws = ((WorldServer)world);
			
			for (EntityPlayer player : ws.playerEntities) {
				EntityPlayerMP ep = ((EntityPlayerMP)player);
				
				if (ep.getDistanceSq(pos) < 64 * 64 && ws.getPlayerChunkMap().isPlayerWatchingChunk(ep, pos.getX() >> 4, pos.getZ() >> 4))
					INSTANCE.sendTo(toSend, ep);
			}
		}
	}
	
	public static void dispatchTEToNearbyPlayers(TileEntity tile) {
		
		IBlockState state = tile.getWorld().getBlockState(tile.getPos());
		tile.getWorld().notifyBlockUpdate(tile.getPos(), state, state, 8);
		if (tile.getWorld().isRemote)
			tile.getWorld().markBlockRangeForRenderUpdate(tile.getPos(), tile.getPos());
	}
}
