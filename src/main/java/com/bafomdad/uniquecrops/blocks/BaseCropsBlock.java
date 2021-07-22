package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileSunBlock;
import com.bafomdad.uniquecrops.init.UCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.function.Supplier;

public class BaseCropsBlock extends Block implements IGrowable, IPlantable {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    public static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    private final Supplier<Item> cropGetter;
    private final Supplier<BlockItem> seedGetter;

    private boolean bonemealable = true;
    private boolean clickHarvest = true;
    private boolean includeSeed = true;
    private boolean ignoreGrowthRestrictions = false;

    public BaseCropsBlock(Supplier<Item> crop, Supplier<BlockItem> seed) {

        super(Properties.from(Blocks.WHEAT));
        this.cropGetter = crop;
        this.seedGetter = seed;
        UCBlocks.CROPS.add(this);
        setDefaultState(getDefaultState().with(AGE, 0));
    }

    public BaseCropsBlock(Supplier<Item> crop, Supplier<BlockItem> seed, Properties prop) {

        super(prop);
        this.cropGetter = crop;
        this.seedGetter = seed;
        UCBlocks.CROPS.add(this);
        setDefaultState(getDefaultState().with(AGE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {

        super.fillStateContainer(builder);
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
    }

    @Override
    public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state) {

        return new ItemStack(getSeed());
    }

    public Item getCrop() {

       return this.cropGetter.get();
    }

    public Item getSeed() {

       return this.seedGetter.get();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (!isMaxAge(state)) return ActionResultType.PASS;

        if (clickHarvest) {
            if (!world.isRemote) {
                world.setBlockState(pos, this.withAge(0), 3);
                int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
                harvestItems(world, pos, state, fortune);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    protected void harvestItems(World world, BlockPos pos, BlockState state, int fortune) {

        Item item = this.getCrop();
        if (item != Items.AIR) {
            int fortuneDrop = fortune > 0 ? world.rand.nextInt(fortune) : 0;
            InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(item));
        }
        if (includeSeed)
            InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(this.getSeed()));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {

        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {

        return state.isIn(Blocks.FARMLAND);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {

        BlockPos blockpos = pos.down();
        return (worldIn.getLightSubtracted(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    public IntegerProperty getAgeProperty() {

        return AGE;
    }

    protected int getAge(BlockState state) {

        return state.get(this.getAgeProperty());
    }

    public int getMaxAge() {

        return 7;
    }

    public int getHarvestAge() {

        return getMaxAge();
    }

    public boolean isMaxAge(BlockState state) {

        return state.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    public BlockState withAge(int age) {

        return this.getDefaultState().with(this.getAgeProperty(), Integer.valueOf(age));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {

        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightSubtracted(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0)) {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                    this.tickCrop(state, worldIn, pos, random);
                }
            }
        }
    }

    protected void tickCrop(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

    }

    protected float getGrowthChance(Block blockIn, IBlockReader worldIn, BlockPos pos) {

        float f = 1.0F;
        BlockPos blockpos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
                if (blockstate.canSustainPlant(worldIn, blockpos.add(i, 0, j), net.minecraft.util.Direction.UP, (net.minecraftforge.common.IPlantable) blockIn)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(worldIn, pos.add(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }
        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();
            if (flag2) {
                f /= 2.0F;
            }
        }
        return f;
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState state, boolean b) {

        return !this.isMaxAge(state);
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {

        return this.bonemealable;
    }

    public boolean canIgnoreGrowthRestrictions(World world, BlockPos pos) {

        if (!ignoreGrowthRestrictions) return false;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos loopPos = pos.offset(dir);
            TileEntity tile = world.getTileEntity(loopPos);
            if (tile instanceof TileSunBlock && ((TileSunBlock)tile).powered)
                return true;
        }
        return false;
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {

        int i = this.getAge(state) + this.getBonemealAgeIncrease(world);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        world.setBlockState(pos, this.withAge(i), 2);
    }

    protected int getBonemealAgeIncrease(World world) {

        return MathHelper.nextInt(world.rand, 2, 5);
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {

        BlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return getDefaultState();
        return state;
    }

    public void setBonemealable(boolean flag) {

        this.bonemealable = flag;
    }

    public void setClickHarvest(boolean flag) {

        this.clickHarvest = flag;
    }

    public void setIncludeSeed(boolean flag) {

        this.includeSeed = flag;
    }

    public void setIgnoreGrowthRestrictions(boolean flag) {

        this.ignoreGrowthRestrictions = flag;
    }

    public boolean isIncludeSeed() {

        return this.includeSeed;
    }
}
