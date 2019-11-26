package com.bafomdad.uniquecrops.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.bafomdad.uniquecrops.data.UCOreHandler;

public class UCOreGen {

	@SubscribeEvent
	public void onOreGen(OreGenEvent.GenerateMinable event) {
		
		if (event.getType() == GenerateMinable.EventType.DIAMOND) {
			Random rand = new Random();
			if (rand.nextInt(3) == 0) {
				BlockPos ePos = event.getPos();
				ChunkPos cPos = new ChunkPos(ePos);
				
				int newX = Math.min(cPos.getXEnd(), ePos.getX() + rand.nextInt(8));
				int newZ = Math.min(cPos.getZEnd(), ePos.getZ() + rand.nextInt(8));
				int newY = 6 + rand.nextInt(6);

				BlockPos newPos = new BlockPos(newX, newY, newZ);
				UCOreHandler.getInstance().addChunk(newPos, true);
			}
		}
	}
}
