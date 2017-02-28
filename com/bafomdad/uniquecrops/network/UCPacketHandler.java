package com.bafomdad.uniquecrops.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class UCPacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("UniqueCrops".toLowerCase());
	
	public static void initClient() {
		
		INSTANCE.registerMessage(PacketUCEffect.Handler.class, PacketUCEffect.class, 0, Side.CLIENT);
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
}
