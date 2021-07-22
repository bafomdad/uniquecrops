package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.nbt.CompoundNBT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TileMillennium extends BaseTileUC {

    private String timestamp;
    private static final String DATEFORMAT = "yyyy-mm-dd hh:mm:ss";

    public TileMillennium() {

        super(UCTiles.MILLENNIUM.get());
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        if (!isTimeEmpty())
            tag.putString("UC_timestamp", timestamp);
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        if (tag.contains("UC_timestamp"))
            timestamp = tag.getString("UC_timestamp");
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
