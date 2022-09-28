package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.api.IEnchanterRecipe;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.Container;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RecipeEnchanter implements IEnchanterRecipe {

    private final ResourceLocation id;
    private final Enchantment ench;
    private final int cost;
    private final NonNullList<Ingredient> inputs;

    public RecipeEnchanter(ResourceLocation id, Enchantment ench, int cost, Ingredient... inputs) {

        this.id = id;
        this.ench = ench;
        this.cost = cost;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        if (ench == null)
            throw new IllegalStateException("Enchantment cannot be null");
        if (inputs == null || inputs.length <= 0)
            throw new IllegalStateException("Inputs cannot be empty or null");
    }

    @Override
    public boolean matches(Container inv, Level world) {

        if (!inputs.isEmpty()) {
            Ingredient ingredient = inputs.get(0);
            int inputs = 0;
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stack = inv.getItem(i);
                if (stack.isEmpty()) break;
                if (ingredient.test(stack))
                    inputs++;
            }
            return inputs == ingredient.getItems().length;
        }
        return false;
    }

    @Override
    public boolean matchesEnchantment(String location) {

        return this.ench.getRegistryName().toString().equals(location);
    }

    @Override
    public void applyEnchantment(ItemStack toApply) {

        toApply.enchant(this.ench, this.ench.getMaxLevel());
    }

    @Override
    public Enchantment getEnchantment() {

        return this.ench;
    }

    @Override
    public int getCost() {

        return this.cost;
    }

    @Override
    public ItemStack assemble(Container inv) {

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {

        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentInstance(this.ench, this.ench.getMaxLevel()));

        return enchantedBook;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {

        return this.inputs;
    }

    @Override
    public ResourceLocation getId() {

        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return UCRecipes.ENCHANTER_SERIALIZER.get();
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<IEnchanterRecipe> {

        @Override
        public IEnchanterRecipe fromJson(ResourceLocation id, JsonObject json) {

            Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(json.get("enchantment").getAsString()));
            int cost = json.get("cost").getAsInt();
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingredients)
                inputs.add(Ingredient.fromJson(e));

            return new RecipeEnchanter(id, ench, cost, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public IEnchanterRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

            Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(buf.readUtf()));
            int cost = buf.readVarInt();
            Ingredient[] inputs = new Ingredient[buf.readVarInt()];
            for (int i = 0; i < inputs.length; i++)
                inputs[i] = Ingredient.fromNetwork(buf);

            return new RecipeEnchanter(id, ench, cost, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, IEnchanterRecipe recipe) {

            buf.writeUtf(recipe.getEnchantment().getRegistryName().toString());
            buf.writeVarInt(recipe.getCost());
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients())
                input.toNetwork(buf);
        }
    }
}
