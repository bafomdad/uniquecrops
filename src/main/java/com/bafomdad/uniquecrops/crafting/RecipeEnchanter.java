package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.api.IEnchanterRecipe;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
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
        this.inputs = NonNullList.from(Ingredient.EMPTY, inputs);
        if (ench == null)
            throw new IllegalStateException("Enchantment cannot be null");
        if (inputs == null || inputs.length <= 0)
            throw new IllegalStateException("Inputs cannot be empty or null");
    }

    @Override
    public boolean matches(IInventory inv, World world) {

        if (!inputs.isEmpty()) {
            Ingredient ingredient = inputs.get(0);
            int inputs = 0;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack.isEmpty()) break;
                if (ingredient.test(stack))
                    inputs++;
            }
            return inputs == ingredient.getMatchingStacks().length;
        }
        return false;
    }

    @Override
    public boolean matchesEnchantment(String location) {

        return this.ench.getRegistryName().toString().equals(location);
    }

    @Override
    public void applyEnchantment(ItemStack toApply) {

        toApply.addEnchantment(this.ench, this.ench.getMaxLevel());
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
    public ItemStack getCraftingResult(IInventory inv) {

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getRecipeOutput() {

        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentData(this.ench, this.ench.getMaxLevel()));

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
    public IRecipeSerializer<?> getSerializer() {

        return UCRecipes.ENCHANTER_SERIALIZER.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IEnchanterRecipe> {

        @Override
        public IEnchanterRecipe read(ResourceLocation id, JsonObject json) {

            Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(json.get("enchantment").getAsString()));
            int cost = json.get("cost").getAsInt();
            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingredients)
                inputs.add(Ingredient.deserialize(e));

            return new RecipeEnchanter(id, ench, cost, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public IEnchanterRecipe read(ResourceLocation id, PacketBuffer buf) {

            Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(buf.readString()));
            int cost = buf.readVarInt();
            Ingredient[] inputs = new Ingredient[buf.readVarInt()];
            for (int i = 0; i < inputs.length; i++)
                inputs[i] = Ingredient.read(buf);

            return new RecipeEnchanter(id, ench, cost, inputs);
        }

        @Override
        public void write(PacketBuffer buf, IEnchanterRecipe recipe) {

            buf.writeString(recipe.getEnchantment().getRegistryName().toString());
            buf.writeVarInt(recipe.getCost());
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients())
                input.write(buf);
        }
    }
}
