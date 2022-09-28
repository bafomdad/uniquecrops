package com.bafomdad.uniquecrops.blocks.supercrops;

import com.bafomdad.uniquecrops.blocks.BaseSuperCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileWeatherflesia;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumDirectional;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class Weatherflesia extends BaseSuperCropsBlock implements EntityBlock {

    public static final EnumProperty RAFFLESIA = EnumProperty.<EnumDirectional>create("rafflesia", EnumDirectional.class);

    public Weatherflesia() {

        registerDefaultState(defaultBlockState().setValue(RAFFLESIA, EnumDirectional.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(RAFFLESIA);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        BlockPos offset = pos;
        if (state.getValue(RAFFLESIA) != EnumDirectional.UP || state.getValue(RAFFLESIA) != EnumDirectional.DOWN) {
            offset = offsetDirectional(state, pos);
        }
        BlockEntity tile = world.getBlockEntity(offset);
        if (tile instanceof TileWeatherflesia) {
            TileWeatherflesia weather = (TileWeatherflesia)tile;
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() == UCItems.PIXEL_BRUSH.get() && !player.isCrouching()) {
                Biome biome = world.getBiome(pos).value();
                String biomeId = biome.getRegistryName().toString();
                NBTUtils.setString(stack, UCStrings.TAG_BIOME, biomeId);
                weather.setBrush(stack);
                player.setItemInHand(hand, ItemStack.EMPTY);
                weather.markBlockForUpdate();
                return InteractionResult.SUCCESS;
            }
            if (stack.isEmpty() && player.isCrouching()) {
                ItemStack tileItem = weather.getBrush();
                if (!tileItem.isEmpty()) {
                    weather.setBrush(ItemStack.EMPTY);
                    player.setItemInHand(hand, tileItem);
                    weather.markBlockForUpdate();
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private BlockPos offsetDirectional(BlockState state, BlockPos pos) {

        BlockPos offPos = pos;
        EnumDirectional dir = ((EnumDirectional)state.getValue(RAFFLESIA)).getOpposite();
        switch (dir) {
            case NORTH: offPos = offPos.north(); break;
            case SOUTH: offPos = offPos.south(); break;
            case EAST: offPos = offPos.east(); break;
            case WEST: offPos = offPos.west(); break;
            case NORTHWEST: offPos = offPos.north().west(); break;
            case NORTHEAST: offPos = offPos.north().east(); break;
            case SOUTHWEST: offPos = offPos.south().west(); break;
            case SOUTHEAST: offPos = offPos.south().east(); break;
            default: break;
        }
        return offPos;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileWeatherflesia) {
            TileWeatherflesia weather = (TileWeatherflesia)tile;
            weather.tickBiomeStrength();
            ItemStack stack = weather.getBrush();
            if (!stack.isEmpty() && stack.isDamaged()) {
                int repairStrength = weather.getBiomeStrength() / 2;
                stack.setDamageValue(stack.getDamageValue() - repairStrength);
                weather.markBlockForUpdate();
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        if (state.getValue(RAFFLESIA) == EnumDirectional.UP || state.getValue(RAFFLESIA) == EnumDirectional.DOWN) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos loopPos = pos.relative(dir);
                if (world.isEmptyBlock(loopPos)) {
                    world.destroyBlock(pos, false);
                    break;
                }
            }
        }
        if (isNeighborMissing((Level)world, pos, state))
            world.destroyBlock(pos, false);

        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileWeatherflesia) {
            TileWeatherflesia weather = (TileWeatherflesia)tile;
            ItemStack stack = weather.getBrush();
            if (!stack.isEmpty())
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    private boolean isNeighborMissing(Level world, BlockPos pos, BlockState state) {

        if (!(state.getBlock() instanceof Weatherflesia)) return false;

        EnumDirectional prop = (EnumDirectional)state.getValue(RAFFLESIA);
        switch (prop) {
            case NORTH:
            case SOUTH:
                return isRafflesia(world, pos.east()) || isRafflesia(world, pos.west());
            case WEST:
            case EAST:
                return isRafflesia(world, pos.north()) || isRafflesia(world, pos.south());
            case NORTHEAST: return isRafflesia(world, pos.south()) || isRafflesia(world, pos.west());
            case NORTHWEST: return isRafflesia(world, pos.south()) || isRafflesia(world, pos.east());
            case SOUTHEAST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.west());
            case SOUTHWEST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.east());
            default: return false;
        }
    }

    private boolean isRafflesia(Level world, BlockPos pos) {

        return !(world.getBlockState(pos).getBlock() == this);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return super.getShape(state, world, pos, ctx);
//        return SHAPES[((EnumDirectional)state.getValue(RAFFLESIA)).ordinal()];
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileWeatherflesia(pos, state);
    }
}