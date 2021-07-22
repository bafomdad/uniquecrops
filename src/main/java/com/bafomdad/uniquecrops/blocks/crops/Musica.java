package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileMusica;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.NoteBlockEvent;

import java.util.Random;

public class Musica extends BaseCropsBlock {

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

        if (event.getWorld().isRemote()) return;

        BlockPos ePos = event.getPos();
        for (BlockPos pos : BlockPos.getAllInBoxMutable(ePos.add(-RANGE, -2, -RANGE), ePos.add(RANGE, 2, RANGE))) {
            TileEntity te = event.getWorld().getTileEntity(pos);
            if (te instanceof TileMusica) {
                TileMusica plant = (TileMusica)te;
                if (plant.getBeats().size() > 0) {
                    for (int i = 0; i < plant.getBeats().size(); i++) {
                        TileMusica.Beat beat = plant.getBeats().get(i);
                        if (beat.beatMatches(new TileMusica.Beat(event.getNote(), event.getInstrument(), event.getOctave(), ((ServerWorld)event.getWorld()).getGameTime()))) {
                            plant.setNewBeatTime(i, ((ServerWorld)event.getWorld()).getGameTime());
                            return;
                        }
                    }
                }
                if (plant.canAddNote())
                    plant.setNote(event.getNote(), event.getInstrument(), event.getOctave(), ((ServerWorld)event.getWorld()).getGameTime());
            }
        }
    }

    @Override
    public Item getCrop() {

        Random rand = new Random();
        if (rand.nextInt(50) == 0) {
            Item[] modded = new Item[] {
                    UCItems.RECORD_TAXI.get(),
                    UCItems.RECORD_SIMPLY.get(),
                    UCItems.RECORD_NEONSIGNS.get(),
                    UCItems.RECORD_FARAWAY.get()
            };
            return UCUtils.selectRandom(rand, modded);
        }
        return UCUtils.selectRandom(rand, DROPS);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (canIgnoreGrowthRestrictions(world, pos))
            super.randomTick(state, world, pos, rand);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileMusica();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        if (isMaxAge(state))
            world.addParticle(ParticleTypes.NOTE, pos.getX() + rand.nextFloat(), pos.getY() + 0.3, pos.getZ() + rand.nextFloat(), 0, 0, 0);
    }
}
