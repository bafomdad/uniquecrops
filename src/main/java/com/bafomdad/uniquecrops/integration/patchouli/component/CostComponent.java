package com.bafomdad.uniquecrops.integration.patchouli.component;

import com.bafomdad.uniquecrops.api.IMultiblockRecipe;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.function.UnaryOperator;

public class CostComponent implements ICustomComponent {

    ResourceLocation RES = new ResourceLocation("patchouli:textures/gui/crafting.png");

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
    public void render(PoseStack ms, IComponentRenderContext ctx, float pticks, int mouseX, int mouseY) {

        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, RES);
        int w = 66;
        int h = 26;
        ctx.getGui().blit(ms, (x + 120) / 2 - w / 2, 10, 0, 128 - h, w, h, 128, 256);
        if (cost > 0) {
            stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> crop.setPower(cost));
        }
        ctx.renderItemStack(ms, (x + 120) / 2 - 8, 14, mouseX, mouseY, stack);
        String title = "Recipe Catalyst";
        String text =  (cost > 0) ? "Cost: " + cost : "Consumable";
        ms.pushPose();
        mc.font.draw(ms, title, (x + 60) - mc.font.width(title) / 2.0F, 0, 0);
        mc.font.draw(ms, text, (x + 60) - mc.font.width(text) / 2.0F, y, 0);
        ms.popPose();
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {

        IVariable recipeVar = lookup.apply(multiblock);
        Recipe<?> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(recipeVar.asString())).orElseThrow(IllegalArgumentException::new);
        if (recipe instanceof IMultiblockRecipe mb) {
            stack = mb.getCatalyst();
            cost = mb.getPower();
        }
    }
}
