package com.bafomdad.uniquecrops.blocks.tiles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import net.minecraft.nbt.NBTTagCompound;

public class TileMillennium extends TileBaseUC {

	private String timestamp;
	private static final String DATEFORMAT = "yyyy-mm-dd hh:mm:ss";
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		if (tag.hasKey("UC_timestamp"))
			timestamp = tag.getString("UC_timestamp");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		if (!isTimeEmpty())
			tag.setString("UC_timestamp", timestamp);
	}
	
	public boolean isTimeEmpty() {
		
		return timestamp == null || (timestamp != null && timestamp.isEmpty());
	}
	
	public void setTime() {
		
		LocalDateTime ldt = LocalDateTime.now();
		Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		String dateFormat = new SimpleDateFormat(DATEFORMAT).format(date);
		timestamp = dateFormat;
	}
	
	public long calcTime() {
		
		if (!isTimeEmpty()) {
			try {
				long diff = System.currentTimeMillis() - new SimpleDateFormat(DATEFORMAT).parse(timestamp).getTime();
				return TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
