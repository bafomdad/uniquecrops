package com.bafomdad.uniquecrops.integration.patchouli.component;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumGrowthSteps;
import com.bafomdad.uniquecrops.init.UCItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.function.UnaryOperator;

public class GrowthComponent implements ICustomComponent {

    private transient int x, y;

    @Override
    public void build(int cx, int cy, int pageNum) {

        this.x = cx;
        this.y = cy;
    }

    @Override
    public void render(PoseStack ms, IComponentRenderContext ctx, float pticks, int mouseX, int mouseY) {

        Minecraft mc = ctx.getGui().getMinecraft();
        ItemStack book = mc.player.getMainHandItem();
        if (book.getItem() == UCItems.BOOK_GUIDE.get()) {
            if (!book.hasTag()) {
                renderBlank(ms, mc.font);
                return;
            }
            if (!book.getTag().contains(UCStrings.TAG_GROWTHSTAGES)) {
                renderBlank(ms, mc.font);
                return;
            }
            ms.pushPose();
            mc.font.draw(ms, new TextComponent("Feroxia Growth Steps"), x, y, 0);
            ms.scale(0.85F, 0.85F, 0.85F);
            ListTag tagList = book.getTag().getList(UCStrings.TAG_GROWTHSTAGES, 10);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag tag = tagList.getCompound(i);
                int stage = tag.getInt("stage" + i);
                String desc = EnumGrowthSteps.values()[stage].getDescription();
                mc.font.draw(ms, new TextComponent("Stage " + (i + 1) + ": ").append(new TranslatableComponent(desc)), x, 20 + y + (i * 15), 0);
            }
            ms.popPose();
        }
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {

    }

    private void renderBlank(PoseStack ms, Font font) {

        font.draw(ms, new TextComponent("Feroxia Growth Steps"), x, y, 0);
        font.draw(ms, new TextComponent("Here be crops!"), x, y + 20, 0);
    }
}
