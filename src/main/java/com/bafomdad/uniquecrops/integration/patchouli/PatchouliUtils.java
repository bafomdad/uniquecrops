package com.bafomdad.uniquecrops.integration.patchouli;

import com.bafomdad.uniquecrops.init.UCRecipes;
import com.bafomdad.uniquecrops.integration.jei.JEIPluginUC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PatchouliUtils {

    private static final Function<Map.Entry<Property<?>, Comparable<?>>, String> PROPERTIES_COMPARABLE = new Function<Map.Entry<Property<?>, Comparable<?>>, String>() {

        public String apply(@Nullable Map.Entry<Property<?>, Comparable<?>> entry) {

            if (entry == null) {
                return "<NULL>";
            } else {
                Property<?> property = entry.getKey();
                return property.getName() + "=" + this.func_235905_a_(property, entry.getValue());
            }
        }

        private <T extends Comparable<T>> String func_235905_a_(Property<T> prop, Comparable<?> comparable) {

            return prop.getName((T)comparable);
        }
    };

    public static String serialize(BlockState state) {

        StringBuilder sb = new StringBuilder();
        sb.append(state.getBlock().getRegistryName().toString());
        if (!state.getValues().isEmpty()) {
            sb.append("[");
            sb.append(state.getValues().entrySet().stream().map(PROPERTIES_COMPARABLE).collect(Collectors.joining(",")));
            sb.append("]");
        }
        return sb.toString();
    }

    public static BlockState deserialize(String string) {

        if (string.contains("[")) {
            String[] split = string.split("\\[");
            split[1] = split[1].substring(0, split[1].lastIndexOf("]"));

            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(split[0]));
            if (block == Blocks.AIR) return Blocks.AIR.getDefaultState();

            StateContainer blockState = block.getStateContainer();
            BlockState returnState = block.getDefaultState();

            String[] stateValues = split[1].split(",");
            for (String value : stateValues) {
                String[] valueSplit = value.split("=");
                Property property = blockState.getProperty(valueSplit[0]);
                if (property != null)
                    returnState = returnState.with(property, (Comparable) property.parseValue(valueSplit[1]).get());
            }
            return returnState;
        }
        return Blocks.AIR.getDefaultState();
    }

    public static void registerMultiblocks() {

        JEIPluginUC.loadType(UCRecipes.MULTIBLOCK_TYPE).forEach(recipe -> {
            ResourceLocation id = recipe.getId();
            String[][] shape = new String[1][];
            shape[0] = recipe.getShape();
            List<Object> objects = new ArrayList<>();
            recipe.getDefinition().forEach((key, value) -> {
                objects.add(key);
                objects.add(value.getFirstState());
            });
            IMultiblock mb = PatchouliAPI.get().makeMultiblock(shape, objects.toArray());
            PatchouliAPI.get().registerMultiblock(id, mb);
        });
    }
}
