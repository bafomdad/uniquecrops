package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileBarrel;
import com.bafomdad.uniquecrops.blocks.tiles.TileCraftyPlant;
import com.bafomdad.uniquecrops.gui.ContainerBarrel;
import com.bafomdad.uniquecrops.gui.ContainerCraftyPlant;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UCScreens {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, UniqueCrops.MOD_ID);

    public static final RegistryObject<MenuType<ContainerBarrel>> BARREL = register("abstract_barrel", (IContainerFactory<ContainerBarrel>)(windowId, inventory, data) -> {
        TileBarrel te = (TileBarrel)inventory.player.level.getBlockEntity(data.readBlockPos());
        return new ContainerBarrel(windowId, inventory, te);
    });
    public static final RegistryObject<MenuType<ContainerCraftyPlant>> CRAFTYPLANT = register("crafty_plant", (IContainerFactory<ContainerCraftyPlant>)(windowId, inventory, data) -> {
        TileCraftyPlant te = (TileCraftyPlant)inventory.player.level.getBlockEntity(data.readBlockPos());
        return new ContainerCraftyPlant(windowId, inventory, te);
    });

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> register(String id, MenuType.MenuSupplier<T> factory) {

        return CONTAINERS.register(id, () -> new MenuType<>(factory));
    }
}
