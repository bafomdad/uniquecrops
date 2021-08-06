package com.bafomdad.uniquecrops.events;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.api.ICropPower;
import com.bafomdad.uniquecrops.api.IMultiblockRecipe;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.core.DyeUtils;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumBonemealDye;
import com.bafomdad.uniquecrops.core.enums.EnumLily;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCFeatures;
import com.bafomdad.uniquecrops.items.DyedBonemealItem;
import com.bafomdad.uniquecrops.items.curios.EmblemIronStomach;
import com.bafomdad.uniquecrops.network.PacketSyncCap;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.loot.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class UCEventHandlerCommon {

    public static int COST;

    public static void updateAnvilCost(AnvilUpdateEvent event) {

        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || right.isEmpty()) return;

        if ((left.getItem() == Items.ENCHANTED_BOOK && right.getItem() != Items.ENCHANTED_BOOK) || (left.getItem() != Items.ENCHANTED_BOOK && right.getItem() == Items.ENCHANTED_BOOK)) {
            ItemStack output = updateRepairOutput(left, right, event.getName());
            ItemStack toCheck = (left.getItem() != Items.ENCHANTED_BOOK) ? left.copy() : right.copy();
            if (!output.isEmpty() && NBTUtils.getBoolean(toCheck, UCStrings.TAG_DISCOUNT, false)) {
                int newCost = UCEventHandlerCommon.COST;
                if (newCost > 5) {
                    newCost -= 5;
                    event.setOutput(output.copy());
                    event.setCost(newCost);
                }
            }
            return;
        }
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

    public static void onBiomeLoading(BiomeLoadingEvent event) {

        if (event.getCategory() == Biome.Category.NETHER || event.getCategory() == Biome.Category.THEEND) return;

        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> UCFeatures.ORE_PIXEL_CONFIG);
    }

    public static void onBonemealEvent(BonemealEvent event) {

        if (!(event.getBlock().getBlock() instanceof GrassBlock)) return;
        ItemStack stack = event.getStack();
        if (stack.getItem() instanceof DyedBonemealItem) {
            DyeUtils.BONEMEAL_DYE.entrySet().forEach(dye -> {
               if (dye.getValue().asItem() == stack.getItem()) {
                   event.setResult(EnumBonemealDye.values()[dye.getKey().ordinal()].grow(event.getWorld(), event.getPos()));
                   return;
               }
            });
        }
    }

    public static void jumpTele(LivingEvent.LivingJumpEvent event) {

        LivingEntity elb = event.getEntityLiving();
        if (elb.world.isRemote) return;

        if (elb instanceof PlayerEntity) {
            if (elb.world.getBlockState(elb.getPosition()).getBlock() == UCBlocks.LILY_ENDER.get()) {
                EnumLily.searchNearbyPads(elb.world, elb.getPosition(), elb, Direction.UP);
            }
        }
    }

    public static void addSeed(BlockEvent.BreakEvent event) {

        if (event.getState().isIn(Blocks.GRASS) || event.getState().isIn(Blocks.TALL_GRASS) || event.getState().isIn(Blocks.FERN) || event.getState().isIn(Blocks.LARGE_FERN)) {
            if (event.getWorld() instanceof ServerWorld) {
                ServerWorld serverworld = (ServerWorld)event.getWorld();
                BlockPos pos = event.getPos();
                float value = serverworld.rand.nextFloat();
                if (value > 0.90F) {
                    InventoryHelper.spawnItemStack(serverworld, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(UCItems.NORMAL_SEED.get()));
                }
            }
        }
    }

    public static void addTags(PlayerEvent.PlayerLoggedInEvent event) {

        EmblemIronStomach.init();
    }

    public static void injectLoot(LootTableLoadEvent event) {

        if (event.getName().equals(LootTables.CHESTS_WOODLAND_MANSION))
            event.getTable().addPool(getInjectPool("chests/woodland_mansion"));
        if (event.getName().equals(LootTables.CHESTS_IGLOO_CHEST))
            event.getTable().addPool(getInjectPool("chests/igloo_chest"));
        if (event.getName().equals(LootTables.CHESTS_SIMPLE_DUNGEON))
            event.getTable().addPool(getInjectPool("chests/simple_dungeon"));
    }

    private static LootPool getInjectPool(String pool) {

        return LootPool.builder()
                .addEntry(getInjectEntry(pool, 1))
                .name("uniquecrops_inject")
                .build();
    }

    private static LootEntry.Builder<?> getInjectEntry(String name, int weight) {

        ResourceLocation injectFolder = new ResourceLocation(UniqueCrops.MOD_ID, "inject/" + name);
        return TableLootEntry.builder(injectFolder).weight(weight);
    }

    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {

        IMultiblockRecipe recipe = findRecipe(event.getWorld(), event.getPos());
        if (recipe != null) {
            PlayerEntity player = event.getPlayer();
            ItemStack held = player.getHeldItem(event.getHand());
            if (!ItemStack.areItemsEqual(held, recipe.getCatalyst())) return;
            int cropPower = recipe.getPower();

            LazyOptional<ICropPower> cap = held.getCapability(CPProvider.CROP_POWER, null);
            if (cropPower > 0 && !cap.isPresent()) {
                player.sendStatusMessage(new StringTextComponent("Crop power is not present in this item: " + held.getDisplayName()), true);
                return;
            }
            cap.ifPresent(crop -> {
                if (crop.getPower() < cropPower) {
                    player.sendStatusMessage(new StringTextComponent("Insufficient crop power. Needed: " + cropPower), true);
                    return;
                }
                if (!player.isCreative()) {
                    crop.remove(cropPower);
                    if (player instanceof ServerPlayerEntity)
                        UCPacketHandler.sendTo((ServerPlayerEntity)player, new PacketSyncCap(crop.serializeNBT()));
                }
            });
            if (cropPower <= 0 && !event.getWorld().isRemote && !player.isCreative()) {
                held.shrink(1);
            }
            recipe.setResult(event.getWorld(), event.getPos());
            event.setCanceled(true);
            player.swingArm(event.getHand());
        }
    }

    private static IMultiblockRecipe findRecipe(World world, BlockPos pos) {

        for (IRecipe<?> recipe : world.getRecipeManager().getRecipes()) {
            if (recipe instanceof IMultiblockRecipe && ((IMultiblockRecipe)recipe).match(world, pos))
                return ((IMultiblockRecipe) recipe);
        }
        return null;
    }

    // copied from vanilla's RepairContainer class
    private static ItemStack updateRepairOutput(ItemStack input1, ItemStack input2, String repairedItemName) {

        ItemStack itemstack = input1;
        int maximumCost = 1;
        int materialCost = 0;
        int i = 0;
        int j = 0;
        int k = 0;
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = input2;
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
            j = j + itemstack.getRepairCost() + (itemstack2.isEmpty() ? 0 : itemstack2.getRepairCost());
            materialCost = 0;
            boolean flag = false;

            if (!itemstack2.isEmpty()) {
                flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(itemstack2).isEmpty();
                if (itemstack1.isDamageable() && itemstack1.getItem().getIsRepairable(itemstack, itemstack2)) {
                    int l2 = Math.min(itemstack1.getDamage(), itemstack1.getMaxDamage() / 4);
                    if (l2 <= 0) {
                        return ItemStack.EMPTY;
                    }
                    int i3;
                    for(i3 = 0; l2 > 0 && i3 < itemstack2.getCount(); ++i3) {
                        int j3 = itemstack1.getDamage() - l2;
                        itemstack1.setDamage(j3);
                        ++i;
                        l2 = Math.min(itemstack1.getDamage(), itemstack1.getMaxDamage() / 4);
                    }

                    materialCost = i3;
                } else {
                    if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.isDamageable())) {
                        return ItemStack.EMPTY;
                    }
                    if (itemstack1.isDamageable() && !flag) {
                        int l = itemstack.getMaxDamage() - itemstack.getDamage();
                        int i1 = itemstack2.getMaxDamage() - itemstack2.getDamage();
                        int j1 = i1 + itemstack1.getMaxDamage() * 12 / 100;
                        int k1 = l + j1;
                        int l1 = itemstack1.getMaxDamage() - k1;
                        if (l1 < 0) {
                            l1 = 0;
                        }

                        if (l1 < itemstack1.getDamage()) {
                            itemstack1.setDamage(l1);
                            i += 2;
                        }
                    }

                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
                    boolean flag2 = false;
                    boolean flag3 = false;

                    for(Enchantment enchantment1 : map1.keySet()) {
                        if (enchantment1 != null) {
                            int i2 = map.getOrDefault(enchantment1, 0);
                            int j2 = map1.get(enchantment1);
                            j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                            boolean flag1 = enchantment1.canApply(itemstack);
                            if (itemstack.getItem() == Items.ENCHANTED_BOOK) {
                                flag1 = true;
                            }

                            for(Enchantment enchantment : map.keySet()) {
                                if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
                                    flag1 = false;
                                    ++i;
                                }
                            }

                            if (!flag1) {
                                flag3 = true;
                            } else {
                                flag2 = true;
                                if (j2 > enchantment1.getMaxLevel()) {
                                    j2 = enchantment1.getMaxLevel();
                                }

                                map.put(enchantment1, j2);
                                int k3 = 0;
                                switch(enchantment1.getRarity()) {
                                    case COMMON:
                                        k3 = 1;
                                        break;
                                    case UNCOMMON:
                                        k3 = 2;
                                        break;
                                    case RARE:
                                        k3 = 4;
                                        break;
                                    case VERY_RARE:
                                        k3 = 8;
                                }

                                if (flag) {
                                    k3 = Math.max(1, k3 / 2);
                                }

                                i += k3 * j2;
                                if (itemstack.getCount() > 1) {
                                    i = 40;
                                }
                            }
                        }
                    }

                    if (flag3 && !flag2) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (StringUtils.isBlank(repairedItemName)) {
                if (itemstack.hasDisplayName()) {
                    k = 1;
                    i += k;
                    itemstack1.clearCustomName();
                }
            } else if (!repairedItemName.equals(itemstack.getDisplayName().getString())) {
                k = 1;
                i += k;
                itemstack1.setDisplayName(new StringTextComponent(repairedItemName));
            }
            if (flag && !itemstack1.isBookEnchantable(itemstack2)) itemstack1 = ItemStack.EMPTY;

            maximumCost = (j + i);
            if (i <= 0) {
                itemstack1 = ItemStack.EMPTY;
            }

            if (k == i && k > 0 && maximumCost >= 40) {
                maximumCost = 39;
            }

            if (maximumCost >= 40) {
                itemstack1 = ItemStack.EMPTY;
            }

            if (!itemstack1.isEmpty()) {
                int k2 = itemstack1.getRepairCost();
                if (!itemstack2.isEmpty() && k2 < itemstack2.getRepairCost()) {
                    k2 = itemstack2.getRepairCost();
                }

                if (k != i || k == 0) {
                    k2 = k2 * 2 + 1;
                }
                itemstack1.setRepairCost(k2);
                EnchantmentHelper.setEnchantments(map, itemstack1);
            }
            setCost(maximumCost);
            return itemstack1;
        }
    }

    private static void setCost(int cost) {

        UCEventHandlerCommon.COST = cost;
    }
}
