package com.bafomdad.uniquecrops.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraftforge.event.world.NoteBlockEvent.Instrument;
import net.minecraftforge.event.world.NoteBlockEvent.Note;
import net.minecraftforge.event.world.NoteBlockEvent.Octave;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.crops.Musica;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class TileMusicaPlant extends TileBaseUC implements ITickable {
	
	private List<Beat> beats = new ArrayList<Beat>();
	private long lastBeat = 0L;
	private int musicStrength;

	@Override
	public void update() {

		IBlockState state = worldObj.getBlockState(getPos());
		if (state == null || (state != null && state.getBlock() != UCBlocks.cropMusica))
			return;
		
		if (getBlockType() != null && !((Musica)getBlockType()).canDance(state))
			return;
		
		if (this.worldObj.isRemote || (beats.isEmpty() || beats.size() == 0))
			return;
		
		for (int i = 0; i < beats.size(); i++) {
			Beat beat = beats.get(i);
			listenAndRemove(beat, i);
			
			List<Integer> timelist = new ArrayList<Integer>();

			int elapsedtime = (int)beat.getTimeElapsed(worldObj.getTotalWorldTime(), beat.getTime());
			if (beat.getTime() != lastBeat && elapsedtime % 2 == 0 /*&& (lastBeat - elapsedtime) % 2 == 0*/)
				timelist.add(elapsedtime);

			if (!timelist.isEmpty() && !beats.isEmpty()) {
				if (worldObj.getTotalWorldTime() % 10 == 0 && lastBeat > 0) {
					int randomtick = (worldObj.rand.nextInt(100) + 5) - timelist.size();
					if (randomtick < this.getPercussion().getTimeElapsed(worldObj.getTotalWorldTime(), lastBeat)) {
						
						((Musica)this.blockType).addAge(worldObj, getPos(), state, 1);
						return;
					}
				}
			}
		}
		if (lastBeat == 0L && this.getPercussion() != null)
			lastBeat = this.getPercussion().getTime();
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		NBTTagList taglist = tag.getTagList(UCStrings.TAG_BEATLIST, 10);
		for (int i = 0; i < taglist.tagCount(); i++) {
			NBTTagCompound tag2 = taglist.getCompoundTagAt(i);
			Note tagnote = Note.values()[tag2.getInteger(UCStrings.TAG_NOTE)];
			Instrument taginst = Instrument.values()[tag2.getInteger(UCStrings.TAG_INST)];
			Octave tagoct = Octave.values()[tag2.getInteger(UCStrings.TAG_OCT)];
			long tagtime = tag2.getLong(UCStrings.TAG_TIME);
			this.setNote(tagnote, taginst, tagoct, tagtime);
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		if (!beats.isEmpty() || beats.size() > 0)
		{
			NBTTagList taglist = new NBTTagList();
			for (int i = 0; i < beats.size(); i++) {
				Beat beat = beats.get(i);
				NBTTagCompound tag2 = new NBTTagCompound();
				tag2.setInteger(UCStrings.TAG_NOTE, beat.note);
				tag2.setInteger(UCStrings.TAG_INST, beat.instrument);
				tag2.setInteger(UCStrings.TAG_OCT, beat.octave);
				tag2.setLong(UCStrings.TAG_TIME, beat.worldtime);
				taglist.appendTag(tag2);
			}
			tag.setTag(UCStrings.TAG_BEATLIST, taglist);
		}
	}
	
	public List<Beat> getBeats() {
		
		return beats;
	}
	
	public boolean canAddNote() {
		
		return this.beats.size() < 12;
	}
	
	public Beat setNote(Note note, Instrument instrument, Octave octave, long time) {
		
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
		
		long diff = beat.getTimeElapsed(worldObj.getTotalWorldTime(), beat.getTime());
		if (diff > 50) {
			if (beat.getTime() == lastBeat)
				lastBeat = 0L;
			beats.remove(index);
		}
	}
	
	public boolean isPercussion(Instrument instrument) {
		
		return instrument == Instrument.SNARE || instrument == Instrument.BASSDRUM;
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
		
		public Beat(Note note, Instrument instrument, Octave octave, long worldtime) {
			
			this.note = note.ordinal();
			this.instrument = instrument.ordinal();
			this.octave = octave.ordinal();
			this.worldtime = worldtime;
		}
		
		public Note getNote() {
			
			return Note.values()[note];
		}
		
		public Instrument getInstrument() {
			
			return Instrument.values()[instrument];
		}
		
		public Octave getOctave() {
			
			return Octave.values()[octave];
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
