package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ItemRecordUC extends RecordItem {

    public ItemRecordUC(Supplier<SoundEvent> sound) {

        super(1, sound, UCItems.defaultBuilder().stacksTo(1).rarity(Rarity.UNCOMMON));
    }
}
