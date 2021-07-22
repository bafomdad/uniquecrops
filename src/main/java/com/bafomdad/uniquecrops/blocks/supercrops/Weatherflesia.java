package com.bafomdad.uniquecrops.blocks.supercrops;

import com.bafomdad.uniquecrops.blocks.BaseSuperCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileWeatherflesia;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumDirectional;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Weatherflesia extends BaseSuperCropsBlock {

    public static final EnumProperty RAFFLESIA = EnumProperty.<EnumDirectional>create("rafflesia", EnumDirectional.class);
    private static final VoxelShape[] SHAPES = new VoxelShape[] {
            VoxelShapes.create(0.0D, 0.0D, 0.5D, 1.0D, 0.625D, 1.0D),
            VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 0.5D),
            VoxelShapes.create(0.0D, 0.0D, 0.0D, 0.5D, 0.625D, 1.0D),
            VoxelShapes.create(0.5D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
            VoxelShapes.create(0.0D, 0.0D, 0.5D, 0.5D, 0.625D, 1.0D),
            VoxelShapes.create(1.0D, 0.0D, 1.0D, 0.5D, 0.625D, 0.5D),
            VoxelShapes.create(0.0D, 0.0D, 0.0D, 0.5D, 0.625D, 0.5D),
            VoxelShapes.create(0.5D, 0.0D, 0.5D, 1.0D, 0.625D, 0.0D),
            VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
            VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D)
    };

    public Weatherflesia() {

        setDefaultState(getDefaultState().with(RAFFLESIA, EnumDirectional.UP));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {

        super.fillStateContainer(builder);
        builder.add(RAFFLESIA);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        BlockPos offset = pos;
        if (state.get(RAFFLESIA) != EnumDirectional.UP || state.get(RAFFLESIA) != EnumDirectional.DOWN) {
            offset = offsetDirectional(state, pos);
        }
        TileEntity tile = world.getTileEntity(offset);
        if (tile instanceof TileWeatherflesia) {
            TileWeatherflesia weather = (TileWeatherflesia)tile;
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == UCItems.PIXEL_BRUSH.get() && !player.isSneaking()) {
                String biomeId = world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(offset)).toString();
                NBTUtils.setString(stack, UCStrings.TAG_BIOME, biomeId);
                weather.setBrush(stack);
                player.setHeldItem(hand, ItemStack.EMPTY);
                weather.markBlockForUpdate();
                return ActionResultType.SUCCESS;
            }
            if (stack.isEmpty() && player.isSneaking()) {
                ItemStack tileItem = weather.getBrush();
                if (!tileItem.isEmpty()) {
                    weather.setBrush(ItemStack.EMPTY);
                    player.setHeldItem(hand, tileItem);
                    weather.markBlockForUpdate();
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    private BlockPos offsetDirectional(BlockState state, BlockPos pos) {

        BlockPos offPos = pos;
        EnumDirectional dir = ((EnumDirectional)state.get(RAFFLESIA)).getOpposite();
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
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileWeatherflesia) {
            TileWeatherflesia weather = (TileWeatherflesia)tile;
            weather.tickBiomeStrength();
            ItemStack stack = weather.getBrush();
            if (!stack.isEmpty() && stack.isDamaged()) {
                int repairStrength = weather.getBiomeStrength() / 2;
                stack.setDamage(stack.getDamage() - repairStrength);
                weather.markBlockForUpdate();
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {

        if (state.get(RAFFLESIA) == EnumDirectional.UP || state.get(RAFFLESIA) == EnumDirectional.DOWN) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos loopPos = pos.offset(dir);
                if (world.isAirBlock(loopPos)) {
                    world.destroyBlock(pos, false);
                    break;
                }
            }
        }
        if (world instanceof World && isNeighborMissing((World)world, pos, state))
            world.destroyBlock(pos, false);

        return super.updatePostPlacement(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {

        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileWeatherflesia) {
            TileWeatherflesia weather = (TileWeatherflesia)tile;
            ItemStack stack = weather.getBrush();
            if (!stack.isEmpty())
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    private boolean isNeighborMissing(World world, BlockPos pos, BlockState state) {

        if (!(state.getBlock() instanceof Weatherflesia)) return false;

        EnumDirectional prop = (EnumDirectional)state.get(RAFFLESIA);
        switch (prop) {
            case NORTH: return isRafflesia(world, pos.east()) || isRafflesia(world, pos.west());
            case SOUTH: return isRafflesia(world, pos.east()) || isRafflesia(world, pos.west());
            case WEST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.south());
            case EAST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.south());
            case NORTHEAST: return isRafflesia(world, pos.south()) || isRafflesia(world, pos.west());
            case NORTHWEST: return isRafflesia(world, pos.south()) || isRafflesia(world, pos.east());
            case SOUTHEAST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.west());
            case SOUTHWEST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.east());
            default: return false;
        }
    }

    private boolean isRafflesia(World world, BlockPos pos) {

        return !(world.getBlockState(pos).getBlock() == this);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return SHAPES[((EnumDirectional)state.get(RAFFLESIA)).ordinal()];
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return state.get(RAFFLESIA) == EnumDirectional.UP || state.get(RAFFLESIA) == EnumDirectional.DOWN;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileWeatherflesia();
    }
}