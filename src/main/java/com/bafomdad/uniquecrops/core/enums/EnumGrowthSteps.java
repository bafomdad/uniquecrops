package com.bafomdad.uniquecrops.core.enums;

import com.bafomdad.uniquecrops.blocks.BaseLilyBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileFeroxia;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BrewingStandTileEntity;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

public enum EnumGrowthSteps {

    MOONPHASE(UCStrings.MOON, UCConfig.COMMON.moonPhase.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            return world.getMoonFactor() == 1.0F;
        }
    },
    HASTORCH(UCStrings.HASTORCH, UCConfig.COMMON.hasTorch.get()) {

        final int range = 10;

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            if (getTile(world, pos) == null) return false;

            List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range, range, range)));
            for (PlayerEntity player : players) {
                if (player.getUniqueID().equals(getTile(world, pos).getOwner())) {
                    for (Hand hand : Hand.values()) {
                        ItemStack held = player.getHeldItem(hand);
                        if (held.getItem() == Item.getItemFromBlock(Blocks.TORCH))
                            return true;
                    }
                }
            }
            return false;
        }
    },
    LIKESDARKNESS(UCStrings.DARKNESS, UCConfig.COMMON.likesDarkness.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            return world.getLight(pos.up()) < 3 && world.isAirBlock(pos.up());
        }
    },
    DRYFARMLAND(UCStrings.DRYFARMLAND, UCConfig.COMMON.dryFarmland.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            return world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND && world.getBlockState(pos.down()).get(FarmlandBlock.MOISTURE) < 7;
        }
    },
    UNDERFARMLAND(UCStrings.UNDERFARMLAND, UCConfig.COMMON.underFarmland.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            return world.getBlockState(pos.up()).getBlock() == Blocks.FARMLAND;
        }
    },
    BURNINGPLAYER(UCStrings.BURNINGPLAYER, UCConfig.COMMON.burningPlayer.get()) {

        int range = 10;

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            if (getTile(world, pos) == null)
                return false;

            List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range, range, range)));
            for (PlayerEntity player : players) {
                if (player.getUniqueID().equals(getTile(world, pos).getOwner())) {
                    if (player.isBurning() && !player.isImmuneToFire())
                        return true;
                }
            }
            return false;
        }
    },
    HELLWORLD(UCStrings.HELLWORLD, UCConfig.COMMON.hellWorld.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            return world.getDimensionKey() == World.THE_NETHER;
        }
    },
    LIKESHEIGHTS(UCStrings.LIKESHEIGHTS, UCConfig.COMMON.likesHeights.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            return pos.getY() > 100;
        }
    },
    LIKESLILYPADS(UCStrings.LILYPADS, UCConfig.COMMON.likesLilypads.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            int lilypads = 0;
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                Block lilypad = world.getBlockState(pos.offset(facing)).getBlock();
                if (lilypad != null && (lilypad == Blocks.LILY_PAD || lilypad instanceof BaseLilyBlock))
                    lilypads++;
            }
            return lilypads >= 4;
        }
    },
    THIRSTYPLANT(UCStrings.THIRSTYPLANT, UCConfig.COMMON.thirstyPlant.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)));
            for (ItemEntity item : items) {
                if (item.isAlive() && !item.getItem().isEmpty()) {
                    if (item.getItem().getItem() == Items.WATER_BUCKET)
                    {
                        UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.WATER_DROP, pos.getX(), pos.getY(), pos.getZ(), 6));
                        item.setItem(new ItemStack(Items.BUCKET, 1));
                        return true;
                    }
                }
            }
            return false;
        }
    },
    HUNGRYPLANT(UCStrings.HUNGRYPLANT, UCConfig.COMMON.hungryPlant.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)));
            for (ItemEntity item : items) {
                if (!item.isAlive() && !item.getItem().isEmpty()) {
                    if (item.getItem().getItem().isFood()) {
                        UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 6));
                        item.getItem().shrink(1);
                        if (item.getItem().getCount() <= 0)
                            item.remove();
                        return true;
                    }
                }
            }
            return false;
        }
    },
    LIKESCHICKEN(UCStrings.LIKESCHICKEN, UCConfig.COMMON.likesChicken.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            Entity item = null, chicken = null;
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.add(-3, 0, -3), pos.add(3, 1, 3)));
            for (Entity ent : entities) {
                if (ent.isAlive()) {
                    if (ent instanceof ChickenEntity)
                        chicken = ent;
                    if (ent instanceof ItemEntity && ((ItemEntity)ent).getItem().getItem() == Items.BOWL)
                        item = ent;
                    if (item != null && chicken != null)
                        break;
                }
            }
            if (chicken != null && item != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(chicken.getPosition().add(0, 0, 0), chicken.getPosition().add(1, 1, 1));
                List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(chicken, aabb);
                for (Entity entity : list) {
                    if (entity != null && entity == item) {
                        ItemEntity ei = new ItemEntity(item.world, item.getPosX(), item.getPosY(), item.getPosZ(), new ItemStack(UCItems.TERIYAKI.get()));
                        ((ItemEntity)item).getItem().shrink(1);
                        if (((ItemEntity)item).getItem().getCount() <= 0)
                            item.remove();
                        chicken.remove();
                        UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.EXPLOSION, chicken.getPosX(), chicken.getPosY() + 0.5D, chicken.getPosZ(), 3));
                        if (!world.isRemote)
                            world.addEntity(ei);
                        return true;
                    }
                }
            }
            else if (chicken != null) {
                UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.HEART, chicken.getPosX(), chicken.getPosY() + 1D, chicken.getPosZ(), 3));
                return true;
            }
            return false;
        }
    },
    REQUIRESREDSTONE(UCStrings.REDSTONE, UCConfig.COMMON.likesRedstone.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            for (Direction facing : Direction.values()) {
                int powersignal = world.getRedstonePower(pos.offset(facing), facing);
                if (powersignal >= 8)
                    return true;
            }
            return false;
        }
    },
    VAMPIREPLANT(UCStrings.VAMPIREPLANT, UCConfig.COMMON.vampirePlant.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            int sunlight = world.getLightFor(LightType.SKY.SKY, pos);
            return sunlight == 0;
        }
    },
    FULLBRIGHTNESS(UCStrings.FULLBRIGHTNESS, UCConfig.COMMON.fullBrightness.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            int light = world.getLightFor(LightType.BLOCK, pos.up());
            return light >= 13;
        }
    },
    LIKESWARTS(UCStrings.LIKESWARTS, UCConfig.COMMON.likesWarts.get()) {

        int range = 1;

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            int warts = 0;
            Iterable<BlockPos> poslist = BlockPos.getAllInBoxMutable(pos.add(-range, 0, -range), pos.add(range, 0, range));
            Iterator posit = poslist.iterator();
            while (posit.hasNext()) {
                BlockPos looppos = (BlockPos)posit.next();
                if (!world.isAirBlock(looppos) && world.getBlockState(looppos).getBlock() == Blocks.NETHER_WART) {
                    warts++;
                }
            }
            return warts == 8;
        }
    },
    LIKESCOOKING(UCStrings.LIKESCOOKING, UCConfig.COMMON.likesCooking.get()) {

        int range = 6;

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            boolean flag = false;
            Iterable<BlockPos> poslist = BlockPos.getAllInBoxMutable(pos.add(-range, -2, -range), pos.add(range, 2, range));
            Iterator iter = poslist.iterator();
            while (iter.hasNext()) {
                BlockPos posit = (BlockPos)iter.next();
                BlockState loopState = world.getBlockState(posit);
                if (loopState.getBlock() == Blocks.FURNACE) {
                    if (loopState.get(AbstractFurnaceBlock.LIT)) flag = true;
                }
            }
            return flag;
        }
    },
    LIKESBREWING(UCStrings.LIKESBREWING, UCConfig.COMMON.likesBrewing.get()) {

        int range = 6;

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            Iterable<BlockPos> poslist = BlockPos.getAllInBoxMutable(pos.add(-range, -2, -range), pos.add(range, 2, range));
            Iterator iter = poslist.iterator();
            while (iter.hasNext()) {
                BlockPos posit = (BlockPos)iter.next();
                TileEntity tile = world.getTileEntity(posit);
                if (tile instanceof BrewingStandTileEntity) {
                    boolean flag = false;
                    try {
                        Field f = ObfuscationReflectionHelper.getPrivateValue(BrewingStandTileEntity.class, (BrewingStandTileEntity) tile, "field_145946_k");
                        flag = f.getInt((BrewingStandTileEntity) tile) > 0;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (flag)
                        return true;
                }
            }
            return false;
        }
    },
    CHECKERBOARD(UCStrings.LIKESCHECKERS, UCConfig.COMMON.likesCheckers.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            BlockPos[] neighbors = new BlockPos[] { pos.add(1, 0, 1), pos.add(-1, 0, -1), pos.add(1, 0, -1), pos.add(-1, 0, 1) };
            BlockPos[] noneighbors = new BlockPos[] { pos.offset(Direction.EAST), pos.offset(Direction.NORTH), pos.offset(Direction.SOUTH), pos.offset(Direction.WEST) };
            int crops = 0;
            for (BlockPos looppos1 : neighbors) {
                if (world.getBlockState(looppos1).getBlock() == UCBlocks.FEROXIA_CROP.get())
                    crops++;
            }
            for (BlockPos looppos2 : noneighbors) {
                if (world.getBlockState(looppos2).getBlock() == UCBlocks.FEROXIA_CROP.get())
                    crops--;
            }
            return crops == 4;
        }
    },
    NOBONEMEAL(UCStrings.DONTBONEMEAL, UCConfig.COMMON.dontBonemeal.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            return true;
        }
    },
    SELFSACRIFICE(UCStrings.SELFSACRIFICE, UCConfig.COMMON.selfSacrifice.get()) {

        @Override
        public boolean canAdvance(World world, BlockPos pos, BlockState state) {

            TileFeroxia tile = getTile(world, pos);
            if (tile != null) {
                PlayerEntity player = UCUtils.getPlayerFromUUID(tile.getOwner().toString());
                if (!world.isRemote && player != null) {
                    CompoundNBT tag = player.getPersistentData();
                    if (!tag.contains("hasSacrificed")) {
                        player.sendMessage(new StringTextComponent(TextFormatting.RED + "The savage plant whispers: \"The Time is right to perform a self sacrifice.\""), player.getUniqueID());
                        tag.putBoolean("hasSacrificed", false);
                    }
                }
            }
            return false;
        }
    };

    private final String desc;
    private final boolean enabled;

    EnumGrowthSteps(String desc, boolean enabled) {

        this.desc = desc;
        this.enabled = enabled;
    }

    public String getDescription() {

        return this.desc;
    }

    public boolean isEnabled() {

        return this.enabled;
    }

    public TileFeroxia getTile(World world, BlockPos pos) {

        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileFeroxia)
            return (TileFeroxia)tile;

        return null;
    }

    public abstract boolean canAdvance(World world, BlockPos pos, BlockState state);
}
