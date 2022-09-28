package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TileMillennium extends BaseTileUC {

    private String timestamp;
    private static final String DATEFORMAT = "yyyy-mm-dd hh:mm:ss";

    public TileMillennium(BlockPos pos, BlockState state) {

        super(UCTiles.MILLENNIUM.get(), pos, state);
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        if (!isTimeEmpty())
            tag.putString("UC_timestamp", timestamp);
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

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
