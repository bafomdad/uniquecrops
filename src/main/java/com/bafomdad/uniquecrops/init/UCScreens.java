package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileBarrel;
import com.bafomdad.uniquecrops.blocks.tiles.TileCraftyPlant;
import com.bafomdad.uniquecrops.gui.ContainerBarrel;
import com.bafomdad.uniquecrops.gui.ContainerCraftyPlant;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UCScreens {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, UniqueCrops.MOD_ID);

    public static final RegistryObject<ContainerType<ContainerBarrel>> BARREL = register("abstract_barrel", (IContainerFactory<ContainerBarrel>)(windowId, inventory, data) -> {
        TileBarrel te = (TileBarrel)inventory.player.world.getTileEntity(data.readBlockPos());
        return new ContainerBarrel(windowId, inventory, te);
    });
    public static final RegistryObject<ContainerType<ContainerCraftyPlant>> CRAFTYPLANT = register("crafty_plant", (IContainerFactory<ContainerCraftyPlant>)(windowId, inventory, data) -> {
        TileCraftyPlant te = (TileCraftyPlant)inventory.player.world.getTileEntity(data.readBlockPos());
        return new ContainerCraftyPlant(windowId, inventory, te);
    });

    private static <T extends Container>RegistryObject<ContainerType<T>> register(String id, ContainerType.IFactory<T> factory) {

        return CONTAINERS.register(id, () -> new ContainerType<>(factory));
    }
}
