package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public class ItemRecordUC extends MusicDiscItem {

    public ItemRecordUC(Supplier<SoundEvent> sound) {

        super(1, sound, UCItems.defaultBuilder().maxStackSize(1).rarity(Rarity.UNCOMMON));
    }
}
