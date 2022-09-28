package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraftforge.event.world.NoteBlockEvent;

import java.util.ArrayList;
import java.util.List;

public class TileMusica extends BaseTileUC{

    private List<Beat> beats = new ArrayList<>();
    private long lastBeat = 0L;
    private int musicStrength;

    public TileMusica(BlockPos pos, BlockState state) {

        super(UCTiles.MUSICA.get(), pos, state);
    }

    public void tickServer() {

        if (getBlockState().getValue(BaseCropsBlock.AGE) >= 7)
            return;

        if (this.level.isClientSide || (beats.isEmpty() || beats.size() == 0))
            return;

        for (int i = 0; i < beats.size(); i++) {
            Beat beat = beats.get(i);
            listenAndRemove(beat, i);

            List<Integer> timelist = new ArrayList<Integer>();

            int elapsedtime = (int)beat.getTimeElapsed(level.getGameTime(), beat.getTime());
            if (beat.getTime() != lastBeat && elapsedtime % 2 == 0 /*&& (lastBeat - elapsedtime) % 2 == 0*/)
                timelist.add(elapsedtime);

            if (!timelist.isEmpty() && !beats.isEmpty()) {
                if (level.getGameTime() % 10 == 0 && lastBeat > 0) {
                    int randomtick = (level.random.nextInt(100) + 5) - timelist.size();
                    if (this.getPercussion() != null && (randomtick < this.getPercussion().getTimeElapsed(level.getGameTime(), lastBeat)))
                    {
                        level.setBlock(worldPosition, getBlockState().setValue(BaseCropsBlock.AGE, getBlockState().getValue(BaseCropsBlock.AGE) + 1), 2);
                        return;
                    }
                }
            }
        }
        if (lastBeat == 0L && this.getPercussion() != null)
            lastBeat = this.getPercussion().getTime();
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        ListTag taglist = tag.getList(UCStrings.TAG_BEATLIST, 10);
        for (int i = 0; i < taglist.size(); i++) {
            CompoundTag tag2 = taglist.getCompound(i);
            NoteBlockEvent.Note tagnote = NoteBlockEvent.Note.values()[tag2.getInt(UCStrings.TAG_NOTE)];
            NoteBlockInstrument taginst = NoteBlockInstrument.values()[tag2.getInt(UCStrings.TAG_INST)];
            NoteBlockEvent.Octave tagoct = NoteBlockEvent.Octave.values()[tag2.getInt(UCStrings.TAG_OCT)];
            long tagtime = tag2.getLong(UCStrings.TAG_TIME);
            this.setNote(tagnote, taginst, tagoct, tagtime);
        }
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        if (!beats.isEmpty() || beats.size() > 0) {
            ListTag taglist = new ListTag();
            for (int i = 0; i < beats.size(); i++) {
                Beat beat = beats.get(i);
                CompoundTag tag2 = new CompoundTag();
                tag2.putInt(UCStrings.TAG_NOTE, beat.note);
                tag2.putInt(UCStrings.TAG_INST, beat.instrument);
                tag2.putInt(UCStrings.TAG_OCT, beat.octave);
                tag2.putLong(UCStrings.TAG_TIME, beat.worldtime);
                taglist.add(tag2);
            }
            tag.put(UCStrings.TAG_BEATLIST, taglist);
        }
    }

    public List<Beat> getBeats() {

        return beats;
    }

    public boolean canAddNote() {

        return this.beats.size() < 12;
    }

    public Beat setNote(NoteBlockEvent.Note note, NoteBlockInstrument instrument, NoteBlockEvent.Octave octave, long time) {

        Beat beat = new Beat(note, instrument, octave, time);
        beats.add(beat);
        return beat;
    }

    public Beat setNewBeatTime(int index, long newtime) {

        Beat beat = beats.get(index);
        beat.worldtime = newtime;
        return beat;
    }

    public void clearBeats() {

        beats.clear();
    }

    public void listenAndRemove(Beat beat, int index) {

        long diff = beat.getTimeElapsed(level.getGameTime(), beat.getTime());
        if (diff > 50) {
            if (beat.getTime() == lastBeat)
                lastBeat = 0L;
            beats.remove(index);
        }
    }

    public boolean isPercussion(NoteBlockInstrument instrument) {

        return instrument == NoteBlockInstrument.SNARE || instrument == NoteBlockInstrument.BASEDRUM;
    }

    public Beat getPercussion() {

        for (Beat beat : beats) {
            if (isPercussion(beat.getInstrument()))
                return beat;
        }
        return null;
    }

    public static class Beat {

        int note, instrument, octave;
        long worldtime;

        public Beat(NoteBlockEvent.Note note, NoteBlockInstrument instrument, NoteBlockEvent.Octave octave, long worldtime) {

            this.note = note.ordinal();
            this.instrument = instrument.ordinal();
            this.octave = octave.ordinal();
            this.worldtime = worldtime;
        }

        public NoteBlockEvent.Note getNote() {

            return NoteBlockEvent.Note.values()[note];
        }

        public NoteBlockInstrument getInstrument() {

            return NoteBlockInstrument.values()[instrument];
        }

        public NoteBlockEvent.Octave getOctave() {

            return NoteBlockEvent.Octave.values()[octave];
        }

        public long getTime() {

            return worldtime;
        }

        public long getTimeElapsed(long newtime, long oldtime) {

            if (newtime < oldtime)
                return (oldtime - newtime);

            return (newtime - oldtime);
        }

        public boolean beatMatches(Beat newbeat) {

            return getNote().equals(newbeat.getNote()) && getInstrument().equals(newbeat.getInstrument()) && getOctave().equals(newbeat.getOctave());
        }

        @Override
        public String toString() {

            return "[Note: " + getNote() + " / Instrument: " + getInstrument() + " / Octave: " + getOctave() + " / Time: " + getTime() + "]";
        }
    }

    public static enum BPM {

        EMPTY(0, 0),
        LARGO(40, 60),
        ADAGIO(66, 76),
        MODERATO(108, 120),
        PRESTO(168, 200);

        int minRange;
        int maxRange;

        private BPM(int min, int max) {

            this.minRange = min;
            this.maxRange = max;
        }

        public static BPM getType(int time) {

            for (BPM e : values()) {
                if (matches(e, time))
                    return e;
            }
            return EMPTY;
        }

        private static boolean matches(BPM bpm, int time) {

            return bpm.minRange < time && time > bpm.maxRange;
        }
    }
}
