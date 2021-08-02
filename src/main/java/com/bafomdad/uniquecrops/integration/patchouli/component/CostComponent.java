package com.bafomdad.uniquecrops.integration.patchouli.component;

import com.bafomdad.uniquecrops.api.IMultiblockRecipe;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.function.UnaryOperator;

public class CostComponent implements ICustomComponent {

    private transient int x, y;
    private transient ItemStack stack;
    private transient int cost;

    IVariable multiblock;

    @Override
    public void build(int cx, int cy, int pageNum) {

        this.x = cx;
        this.y = cy;
    }

    @Override
    public void render(MatrixStack ms, IComponentRenderContext ctx, float pticks, int mouseX, int mouseY) {

        Minecraft mc = Minecraft.getInstance();
        mc.textureManager.bindTexture(new ResourceLocation("patchouli:textures/gui/crafting.png"));
        int w = 66;
        int h = 26;
        ctx.getGui().blit(ms, (x + 120) / 2 - w / 2, 10, 0, 128 - h, w, h, 128, 256);
        if (cost > 0) {
            stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> crop.setPower(cost));
        }
        ctx.renderItemStack(ms, (x + 120) / 2 - 8, 14, mouseX, mouseY, stack);
        String title = "Recipe Catalyst";
        String text =  (cost > 0) ? "Cost: " + cost : "Consumable";
        ms.push();
        mc.fontRenderer.drawString(ms, title, (x + 60) - mc.fontRenderer.getStringWidth(title) / 2.0F, 0, 0);
        mc.fontRenderer.drawString(ms, text, (x + 60) - mc.fontRenderer.getStringWidth(text) / 2.0F, y, 0);
        ms.pop();
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {

        IVariable recipeVar = lookup.apply(multiblock);
        IRecipe<?> mb = Minecraft.getInstance().world.getRecipeManager().getRecipe(new ResourceLocation(recipeVar.asString())).orElseThrow(IllegalArgumentException::new);
        if (mb instanceof IMultiblockRecipe) {
            stack = ((IMultiblockRecipe)mb).getCatalyst();
            cost = ((IMultiblockRecipe)mb).getPower();
        }
    }
}
