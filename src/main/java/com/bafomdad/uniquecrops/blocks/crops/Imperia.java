package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCProtectionHandler;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.Random;

public class Imperia extends BaseCropsBlock {

    public Imperia() {

        super(() -> Items.AIR, UCItems.IMPERIA_SEED, Properties.copy(Blocks.WHEAT).lightLevel(s -> s.getValue(AGE) >= 7 ? 15 : 0));
        setClickHarvest(false);
        setBonemealable(false);
        MinecraftForge.EVENT_BUS.addListener(this::checkDenySpawn);
        MinecraftForge.EVENT_BUS.addListener(this::checkEntityDeath);
    }

    private void checkDenySpawn(LivingSpawnEvent.CheckSpawn event) {

        ChunkPos cPos = new ChunkPos(event.getEntityLiving().blockPosition());
        if (!event.getWorld().isClientSide() && !event.isSpawner() && event.getEntityLiving() instanceof MonsterEntity || event.getEntityLiving() instanceof SlimeEntity) {
            if (UCProtectionHandler.getInstance().getChunkInfo(event.getEntityLiving().level.dimension()).contains(cPos))
                event.setResult(Event.Result.DENY);
        }
    }

    private void checkEntityDeath(LivingDeathEvent event) {

        if (event.getEntityLiving() instanceof MonsterEntity) {
            CompoundNBT tag = event.getEntityLiving().getPersistentData();
            if (tag.contains("ImperiaPosTag") && tag.contains("ImperiaStage")) {
                BlockPos cropPos = NBTUtil.readBlockPos(tag.getCompound("ImperiaPosTag"));
                World world = event.getEntityLiving().level;
                if (!world.isEmptyBlock(cropPos) && world.hasChunkAt(cropPos)) {
                    if (world.getBlockState(cropPos).getBlock() == this && !world.isClientSide) {
                        int stage = tag.getInt("ImperiaStage");
                        this.advanceStage((ServerWorld)world, cropPos, world.getBlockState(cropPos), stage);
                    }
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            if (isMaxAge(state)) {
                setChunksAsNeeded(world, pos, false);
                return;
            }
            String[] mobList = new String[] { "minecraft:witch", "minecraft:skeleton", "minecraft:zombie", "minecraft:spider" };
            EntityType type = Registry.ENTITY_TYPE.get(new ResourceLocation(mobList[rand.nextInt(mobList.length)]));
            Entity entity = type.create(world);
            if (!(entity instanceof LivingEntity)) return;

            entity.setPos(pos.getX(), pos.getY() + 0.5D, pos.getZ());
            CompoundNBT tag = entity.getPersistentData();
            tag.put("ImperiaPosTag", NBTUtil.writeBlockPos(pos));
            tag.putInt("ImperiaStage", getAge(state));
            world.addFreshEntity(entity);
        }
        super.randomTick(state, world, pos, rand);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {

        if (!world.isClientSide)
            setChunksAsNeeded((ServerWorld)world, pos, true);
        return removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    public void advanceStage(ServerWorld world, BlockPos pos, BlockState state, int stage) {

        if (isMaxAge(state) || stage != getAge(state)) return;

        if (getAge(state) + 1 >= getMaxAge())
            setChunksAsNeeded(world, pos, false);

        UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 6));
        world.setBlock(pos, this.setValueAge(getAge(state) + 1), 3);
    }

    public void setChunksAsNeeded(ServerWorld world, BlockPos pos, boolean remove) {

        ChunkPos cPos = new ChunkPos(pos);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                ChunkPos loopPos = new ChunkPos(cPos.x + i, cPos.z + j);
                if (remove)
                    UCProtectionHandler.getInstance().removeChunk(world.dimension(), loopPos, true);
                else
                    UCProtectionHandler.getInstance().addChunk(world.dimension(), loopPos, true);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        if (isMaxAge(state))
            world.addParticle(ParticleTypes.END_ROD, pos.getX() + rand.nextFloat(), pos.getY() + 0.3, pos.getZ() + rand.nextFloat(), 0, 0, 0);
    }
}
