package com.bafomdad.uniquecrops.integration.patchouli.component;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumGrowthSteps;
import com.bafomdad.uniquecrops.init.UCItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
    public void render(MatrixStack ms, IComponentRenderContext ctx, float pticks, int mouseX, int mouseY) {

        Minecraft mc = ctx.getGui().getMinecraft();
        ItemStack book = mc.player.getHeldItemMainhand();
        if (book.getItem() == UCItems.BOOK_GUIDE.get()) {
            if (!book.hasTag()) {
                renderBlank(ms, mc.fontRenderer);
                return;
            }
            if (!book.getTag().contains(UCStrings.TAG_GROWTHSTAGES)) {
                renderBlank(ms, mc.fontRenderer);
                return;
            }
            ms.push();
            mc.fontRenderer.func_243248_b(ms, new StringTextComponent("Feroxia Growth Steps"), x, y, 0);
            ms.scale(0.85F, 0.85F, 0.85F);
            ListNBT tagList = book.getTag().getList(UCStrings.TAG_GROWTHSTAGES, 10);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundNBT tag = tagList.getCompound(i);
                int stage = tag.getInt("stage" + i);
                String desc = EnumGrowthSteps.values()[stage].getDescription();
                mc.fontRenderer.func_243248_b(ms, new StringTextComponent("Stage " + (i + 1) + ": ").append(new TranslationTextComponent(desc)), x, 20 + y + (i * 15), 0);
            }
            ms.pop();
        }
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {

    }

    private void renderBlank(MatrixStack ms, FontRenderer font) {

        font.func_243248_b(ms, new StringTextComponent("Feroxia Growth Steps"), x, y, 0);
        font.func_243248_b(ms, new StringTextComponent("Here be crops!"), x, y + 20, 0);
    }
}
