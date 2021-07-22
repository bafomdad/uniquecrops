package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class ItemCurioUC extends ItemBaseUC implements ICurioItem {

    private static final String TAG_CURIO_UUID_MOST = "curioUUIDMost";
    private static final String TAG_CURIO_UUID_LEAST = "curioUUIDLeast";

    public ItemCurioUC() {

        super(UCItems.unstackable());
    }

    public ItemCurioUC(Properties prop) {

        super(prop);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

        tooltip.add(new TranslationTextComponent(UniqueCrops.MOD_ID + ".tooltip." + this.getRegistryName().getPath()));
    }

    public boolean hasCurio(LivingEntity living) {

        return !CuriosApi.getCuriosHelper().findEquippedCurio(this, living).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY).isEmpty();
    }

    public UUID getCurioUUID(ItemStack stack) {

        long most = NBTUtils.getLong(stack, TAG_CURIO_UUID_MOST, 0);
        if (most == 0) {
            UUID uuid = UUID.randomUUID();
            NBTUtils.setLong(stack, TAG_CURIO_UUID_MOST, uuid.getMostSignificantBits());
            NBTUtils.setLong(stack, TAG_CURIO_UUID_LEAST, uuid.getLeastSignificantBits());
            return getCurioUUID(stack);
        }
        long least = NBTUtils.getLong(stack, TAG_CURIO_UUID_LEAST, 0);
        return new UUID(most, least);
    }
}
