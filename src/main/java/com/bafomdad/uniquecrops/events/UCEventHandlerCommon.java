package com.bafomdad.uniquecrops.events;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.api.ICropPower;
import com.bafomdad.uniquecrops.api.IMultiblockRecipe;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.core.DyeUtils;
import com.bafomdad.uniquecrops.core.enums.EnumBonemealDye;
import com.bafomdad.uniquecrops.core.enums.EnumLily;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCFeatures;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.DyedBonemealItem;
import com.bafomdad.uniquecrops.items.GoodieBagItem;
import com.bafomdad.uniquecrops.network.PacketSyncCap;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;

import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

public class UCEventHandlerCommon {

    public static void updateAnvilCost(AnvilUpdateEvent event) {

        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || right.isEmpty()) return;

        if ((left.getItem() == UCItems.BOOK_UPGRADE.get() && right.getItem() instanceof IBookUpgradeable) || (left.getItem() instanceof IBookUpgradeable && right.getItem() == UCItems.BOOK_UPGRADE.get())) {
            ItemStack output = (left.getItem() instanceof IBookUpgradeable) ? left.copy() : right.copy();
            IBookUpgradeable upgrade = ((IBookUpgradeable)output.getItem());
            if (upgrade.isMaxLevel(output)) return;

            if (upgrade.getLevel(output) <= 0)
                upgrade.setLevel(output, 1);
            else
                upgrade.setLevel(output, upgrade.getLevel(output) + 1);

            event.setOutput(output);
            event.setCost(5);
        }
    }

    public static void onBonemealEvent(BonemealEvent event) {

        if (!(event.getBlock().getBlock() instanceof GrassBlock) || event.getWorld().isClientSide()) return;
        ItemStack stack = event.getStack();
        if (stack.getItem() instanceof DyedBonemealItem) {
            DyeUtils.BONEMEAL_DYE.forEach((key, value) -> {
                if (value.asItem() == stack.getItem()) {
                    event.setResult(EnumBonemealDye.values()[key.ordinal()].grow(event.getWorld(), event.getPos()));
                }
            });
        }
    }

    public static void onBiomeLoading(BiomeLoadingEvent event) {

        if (event.getCategory() == Biome.BiomeCategory.NETHER || event.getCategory() == Biome.BiomeCategory.THEEND) return;

        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UCFeatures.ORE_PIXELGEN);
    }

    public static void jumpTele(LivingEvent.LivingJumpEvent event) {

        LivingEntity elb = event.getEntityLiving();
        if (elb.level.isClientSide) return;

        if (elb instanceof Player) {
            if (elb.level.getBlockState(elb.blockPosition()).getBlock() == UCBlocks.LILY_ENDER.get()) {
                EnumLily.searchNearbyPads(elb.level, elb.blockPosition(), elb, Direction.UP);
            }
        }
    }

    public static void addSeed(BlockEvent.BreakEvent event) {

        if (event.getState().is(Blocks.GRASS) || event.getState().is(Blocks.TALL_GRASS) || event.getState().is(Blocks.FERN) || event.getState().is(Blocks.LARGE_FERN)) {
            if (event.getWorld() instanceof ServerLevel serverlevel) {
                BlockPos pos = event.getPos();
                float value = serverlevel.random.nextFloat();
                if (value > 0.90F) {
                    Containers.dropItemStack(serverlevel, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(UCItems.NORMAL_SEED.get()));
                }
                if (value > 0.92F && GoodieBagItem.isHoliday()) {
                    Containers.dropItemStack(serverlevel, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(UCItems.ADVENTUS_SEED.get()));
                }
            }
        }
    }

    public static void injectLoot(LootTableLoadEvent event) {

        if (event.getName().equals(BuiltInLootTables.WOODLAND_MANSION))
            event.getTable().addPool(getInjectPool("chests/woodland_mansion"));
        if (event.getName().equals(BuiltInLootTables.IGLOO_CHEST))
            event.getTable().addPool(getInjectPool("chests/igloo_chest"));
        if (event.getName().equals(BuiltInLootTables.SIMPLE_DUNGEON))
            event.getTable().addPool(getInjectPool("chests/simple_dungeon"));
    }

    private static LootPool getInjectPool(String pool) {

        return LootPool.lootPool()
                .add(getInjectEntry(pool, 1))
                .name("uniquecrops_inject")
                .build();
    }

    private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name, int weight) {

        ResourceLocation injectFolder = new ResourceLocation(UniqueCrops.MOD_ID, "inject/" + name);
        return LootTableReference.lootTableReference(injectFolder).setWeight(weight);
    }

    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {

        IMultiblockRecipe recipe = findRecipe(event.getWorld(), event.getPos());
        if (recipe != null) {
            Player player = event.getPlayer();
            ItemStack held = player.getItemInHand(event.getHand());
            if (!ItemStack.isSame(held, recipe.getCatalyst())) return;
            int cropPower = recipe.getPower();

            LazyOptional<ICropPower> cap = held.getCapability(CPProvider.CROP_POWER, null);
            if (cropPower > 0 && !cap.isPresent()) {
                player.displayClientMessage(new TextComponent("Crop power is not present in this item: " + held.getDisplayName()), true);
                return;
            }
            cap.ifPresent(crop -> {
                if (crop.getPower() < cropPower) {
                    player.displayClientMessage(new TextComponent("Insufficient crop power. Needed: " + cropPower), true);
                    event.setCanceled(true);
                    return;
                }
                else if (!player.isCreative()) {
                    crop.remove(cropPower);
                    if (player instanceof ServerPlayer)
                        UCPacketHandler.sendTo((ServerPlayer)player, new PacketSyncCap(crop.serializeNBT()));
                }
            });
            if (cropPower <= 0 && !event.getWorld().isClientSide && !player.isCreative()) {
                held.shrink(1);
            }
            recipe.setResult(event.getWorld(), event.getPos());
            event.setCanceled(true);
            player.swing(event.getHand());
        }
    }

    public static void attachItemCaps(AttachCapabilitiesEvent<ItemStack> event) {

        var stack = event.getObject();
        if (stack.getItem() == UCItems.WILDWOOD_STAFF.get()) {
            event.addCapability(new ResourceLocation(UniqueCrops.MOD_ID, "crop_power"), new CPProvider());
        }
    }

    private static IMultiblockRecipe findRecipe(Level level, BlockPos pos) {

        for (Recipe<?> recipe : level.getRecipeManager().getRecipes()) {
            if (recipe instanceof IMultiblockRecipe && ((IMultiblockRecipe)recipe).match(level, pos))
                return ((IMultiblockRecipe) recipe);
        }
        return null;
    }
}
