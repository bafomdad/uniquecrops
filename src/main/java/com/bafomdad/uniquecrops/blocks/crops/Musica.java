package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileMusica;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.NoteBlockEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Musica extends BaseCropsBlock implements EntityBlock {

    static final Item[] DROPS = new Item[] {
            Items.MUSIC_DISC_11,
            Items.MUSIC_DISC_13,
            Items.MUSIC_DISC_BLOCKS,
            Items.MUSIC_DISC_CAT,
            Items.MUSIC_DISC_CHIRP,
            Items.MUSIC_DISC_FAR,
            Items.MUSIC_DISC_MALL,
            Items.MUSIC_DISC_MELLOHI,
            Items.MUSIC_DISC_PIGSTEP,
            Items.MUSIC_DISC_STAL,
            Items.MUSIC_DISC_STRAD,
            Items.MUSIC_DISC_WAIT,
            Items.MUSIC_DISC_WARD
    };
    static final int RANGE = 10;

    public Musica() {

        super(UCItems.RECORD_FARAWAY, UCItems.MUSICA_SEED);
        setBonemealable(false);
        setIgnoreGrowthRestrictions(true);
        MinecraftForge.EVENT_BUS.addListener(this::notePlayEvent);
    }

    private void notePlayEvent(NoteBlockEvent.Play event) {

        if (event.getWorld().isClientSide()) return;

        BlockPos ePos = event.getPos();
        for (BlockPos pos : BlockPos.betweenClosed(ePos.offset(-RANGE, -2, -RANGE), ePos.offset(RANGE, 2, RANGE))) {
            BlockEntity te = event.getWorld().getBlockEntity(pos);
            if (te instanceof TileMusica plant) {
                if (plant.getBeats().size() > 0) {
                    for (int i = 0; i < plant.getBeats().size(); i++) {
                        TileMusica.Beat beat = plant.getBeats().get(i);
                        if (beat.beatMatches(new TileMusica.Beat(event.getNote(), event.getInstrument(), event.getOctave(), ((ServerLevel)event.getWorld()).getGameTime()))) {
                            plant.setNewBeatTime(i, ((ServerLevel)event.getWorld()).getGameTime());
                            return;
                        }
                    }
                }
                if (plant.canAddNote())
                    plant.setNote(event.getNote(), event.getInstrument(), event.getOctave(), ((ServerLevel)event.getWorld()).getGameTime());
            }
        }
    }

    @Override
    public Item getCrop() {

        Random rand = new Random();
        if (rand.nextInt(50) == 0) {
            Item[] MODDED = new Item[] {
                    UCItems.RECORD_TAXI.get(),
                    UCItems.RECORD_SIMPLY.get(),
                    UCItems.RECORD_NEONSIGNS.get(),
                    UCItems.RECORD_FARAWAY.get()
            };
            return UCUtils.selectRandom(rand, MODDED);
        }
        return UCUtils.selectRandom(rand, DROPS);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (canIgnoreGrowthRestrictions(world, pos))
            super.randomTick(state, world, pos, rand);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {

        return new TileMusica(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {

        if (!level.isClientSide()) {
            return (lvl, pos, st, te) -> {
                if (te instanceof TileMusica musica) musica.tickServer();
            };
        }
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Random rand) {

        if (isMaxAge(state))
            world.addParticle(ParticleTypes.NOTE, pos.getX() + rand.nextFloat(), pos.getY() + 0.3, pos.getZ() + rand.nextFloat(), 0, 0, 0);
    }
}
