package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileGoblet;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.Random;
import java.util.UUID;

public class GobletBlock extends Block {

    public static final BooleanProperty FILLED = BooleanProperty.create("filled");
    public static final VoxelShape GOBLET_AABB = VoxelShapes.box(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D);

    public GobletBlock() {

        super(Properties.of(Material.CLAY).sound(SoundType.METAL).strength(0.3F, 1.0F).noCollission());
        registerDefaultState(defaultBlockState().setValue(FILLED, false));
        MinecraftForge.EVENT_BUS.addListener(this::onLivingAttack);
    }

    private void onLivingAttack(LivingAttackEvent event) {

        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        if (event.getSource() != DamageSource.MAGIC && event.getSource().getEntity() instanceof LivingEntity) {
            for (TileEntity tile : event.getEntityLiving().level.blockEntityList) {
                if (tile instanceof TileGoblet) {
                    LivingEntity tagged = UCUtils.getTaggedEntity(((TileGoblet)tile).entityId);
                    if (tagged != null) {
                        event.setCanceled(true);
                        tagged.hurt(event.getSource(), event.getAmount());
                        if (!tagged.isAlive())
                            ((TileGoblet)tile).eraseTaglock();
                        return;
                    }
                }
            }
        }
    }

    public boolean isFilled(BlockState state) {

        return state.getValue(FILLED);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FILLED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return GOBLET_AABB;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (!isFilled(state)) {
            ItemStack stack = player.getMainHandItem();
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileGoblet && stack.getItem() == UCItems.VAMPIRIC_OINTMENT.get()) {
                boolean flag = stack.hasTag() && stack.getTag().contains(UCStrings.TAG_LOCK);
                if (!world.isClientSide && flag) {
                    ((TileGoblet)tile).setTaglock(UUID.fromString(NBTUtils.getString(stack, UCStrings.TAG_LOCK, "")));
                    player.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                    world.setBlock(pos, state.setValue(FILLED, true), 3);
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

        @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {

        if (isFilled(state)) return;
        if (!(world.getBlockEntity(pos) instanceof TileGoblet)) return;
        if (!(entity instanceof ItemEntity) || ((ItemEntity) entity).getItem().getItem() != UCItems.VAMPIRIC_OINTMENT.get()) return;

        ItemStack ointment = ((ItemEntity)entity).getItem();
        if (!ointment.hasTag() || !ointment.getTag().contains(UCStrings.TAG_LOCK)) return;

        if (!world.isClientSide) {
            world.setBlock(pos, state.setValue(FILLED, true), 3);
            ((TileGoblet)world.getBlockEntity(pos)).setTaglock(UUID.fromString(NBTUtils.getString(ointment, UCStrings.TAG_LOCK, "")));
            entity.remove();
        }
    }

    @Override
    public int getSignal(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {

        return isFilled(state) ? 15 : 0;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileGoblet();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {

        if (!isFilled(state)) return;

        double d0 = (double)pos.getX() + 0.45F;
        double d1 = (double)pos.getY() + 0.4F;
        double d2 = (double)pos.getZ() + 0.5F;
        worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, 0.5F), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
