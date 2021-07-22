package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.api.IEnchanterRecipe;
import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.bafomdad.uniquecrops.init.UCTiles;
import com.bafomdad.uniquecrops.items.WildwoodStaffItem;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.*;

public class TileFascino extends BaseTileUC implements ITickableTileEntity {

    private final BlockPos[] ENCHPOS = new BlockPos[] {
            new BlockPos(0, 0, 3), new BlockPos(0, 0, -3), new BlockPos(3, 0, 0), new BlockPos(-3, 0, 0),
            new BlockPos(2, 0, 2), new BlockPos(-2, 0, -2), new BlockPos(2, 0, -2), new BlockPos(-2, 0, 2)
    };

    public TileFascino() {

        super(UCTiles.FASCINO.get());
    }

    private ItemStackHandler inv = new ItemStackHandler(5) {
        @Override
        public int getSlotLimit(int slot) {

            return 1;
        }
    };

    private ItemStack enchantItem = ItemStack.EMPTY;

    private final int RANGE = 7;
    private Stage stage = Stage.IDLE;
    private UUID enchanterId;
    private int enchantingTicks = 0;
    private int enchantmentCost = 7;
    private boolean showMissingCrops;

    @Override
    public void tick() {

        if (showMissingCrops)
            loopMissingCrops();

        if (stage == Stage.IDLE) return;

        enchantingTicks++;
        stage.advance(this);
    }

    public void loopMissingCrops() {

        for (int i = 0; i < ENCHPOS.length; i++) {
            BlockPos loopPos = pos.add(ENCHPOS[i]);
            BlockState loopState = world.getBlockState(loopPos);
            if (loopState.getBlock() != UCBlocks.HEXIS_CROP.get()) {
                UCPacketHandler.sendToNearbyPlayers(world, loopPos, new PacketUCEffect(EnumParticle.SMOKE, loopPos.getX() - 0.5, loopPos.getY() + 0.1, loopPos.getZ() - 0.5, 4));
            }
        }
    }

    public void checkEnchants(PlayerEntity player, ItemStack staff) {

            Optional<IEnchanterRecipe> fascinoRecipe = world.getRecipeManager().getRecipe(UCRecipes.ENCHANTER_TYPE, wrap(), world);
            if (!fascinoRecipe.isPresent())
                player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.unknownrecipe"), true);

            fascinoRecipe.ifPresent(recipe -> {
                    ItemStack heldItem = ItemStack.EMPTY;
                    this.showMissingCrops = false;
                    for (ItemStack stack : player.getHeldEquipment()) {
                        if (!stack.isEmpty() && stack.getItem().isEnchantable(stack) && stack.getItem() != UCItems.WILDWOOD_STAFF.get()) {
                            heldItem = stack;
                            break;
                        }
                    }
                    if (heldItem.isEmpty()) {
                        player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.nothing"), true);
                        return;
                    }
                    if (!recipe.getEnchantment().type.canEnchantItem(heldItem.getItem())) {
                        player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.unenchantable",heldItem.getDisplayName()), true);
                        return;
                    }
                    if (EnchantmentHelper.getEnchantments(heldItem).containsKey(recipe.getEnchantment())) {
                        player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.enchantmentexists"), true);
                        return;
                    }
                    Map<Enchantment, Integer> enchantSet = EnchantmentHelper.getEnchantments(heldItem);
                    Set<Enchantment> enchantments = enchantSet.keySet();
                    for (Enchantment ench : enchantments) {
                        if (!ench.isCompatibleWith(recipe.getEnchantment())) {
                            player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.incompatible", ench.getName()), true);
                            return;
                        }
                    }
                    prepareEnchanting(player, heldItem.getEnchantmentTagList().size() + 1, staff, recipe.getCost());
                    enchantItem = heldItem;
                    return;
            });
    }

    private Inventory wrap() {

        Inventory inv = new Inventory(getInventory().getSlots()) {
            @Override
            public int getInventoryStackLimit() {

                return 1;
            }
        };
        for (int i = 0; i < getInventory().getSlots(); i++)
            inv.setInventorySlotContents(i, getInventory().getStackInSlot(i));

        return inv;
    }

    private void prepareEnchanting(PlayerEntity player, int enchantmentSize, ItemStack staff, int powerCost) {

        int maxGrowth = 7;
        for (int i = 0; i < ENCHPOS.length; i++) {
            BlockPos loopPos = pos.add(ENCHPOS[i]);
            BlockState loopState = world.getBlockState(loopPos);
            if (loopState.getBlock() == UCBlocks.HEXIS_CROP.get()) {
                int age = loopState.get(BaseCropsBlock.AGE);
                if (age < maxGrowth)
                    maxGrowth = age;
            }
            else {
                player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.missingcrops"), true);
                this.showMissingCrops = true;
                enchantItem = ItemStack.EMPTY;
                return;
            }
        }
        if (maxGrowth < enchantmentSize) {
            player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.cropgrowth",enchantmentSize), true);
            enchantItem = ItemStack.EMPTY;
            return;
        }
        if (!WildwoodStaffItem.adjustPower(staff, powerCost)) {
            player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.notenoughpower", powerCost), true);
            enchantItem = ItemStack.EMPTY;
            return;
        }
        stage = Stage.PREPARE;
        enchantmentCost = enchantmentSize;
        enchanterId = player.getUniqueID();
        this.markBlockForUpdate();
        this.markDirty();
    }

    private PlayerEntity getEnchanter() {

        List<PlayerEntity> playerList = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos.add(-RANGE, -1, -RANGE), pos.add(RANGE, 2, RANGE)));
        for (PlayerEntity player : playerList) {
            if (player.getUniqueID().equals(enchanterId)) {
                return player;
            }
        }
        return null;
    }

    public void advanceEnchanting() {

        PlayerEntity player = getEnchanter();
        if (player == null) {
            advanceStage();
        }
        for (int i = 0; i < ENCHPOS.length; i++) {
            BlockPos loopPos = pos.add(ENCHPOS[i]);
            BlockState loopState = world.getBlockState(loopPos);
            if (loopState.getBlock() == UCBlocks.HEXIS_CROP.get()) {
                int age = loopState.get(BaseCropsBlock.AGE);
                if (age < (7 - enchantmentCost)) {
                    finishEnchanting();
                    break;
                }
                world.playEvent(2001, loopPos, Block.getStateId(loopState));
                world.setBlockState(loopPos, loopState.with(BaseCropsBlock.AGE, Math.max(age - 1, 0)));
            }
            else {
                advanceStage();
                break;
            }
        }
    }

    private void finishEnchanting() {

        if (enchantingTicks < 80) return;

        PlayerEntity player = getEnchanter();
        if (player == null) {
            advanceStage();
            enchantItem = ItemStack.EMPTY;
            return;
        }
        ItemStack heldItem = ItemStack.EMPTY;
        for (ItemStack stack : player.getHeldEquipment()) {
            if (!stack.isEmpty() && stack.getItem().isEnchantable(stack) && stack.getItem() != UCItems.WILDWOOD_STAFF.get()) {
                heldItem = stack;
                break;
            }
        }
        if (heldItem.isEmpty()) {
            advanceStage();
            player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.nothing"), true);
            enchantItem = ItemStack.EMPTY;
            return;
        }
        if (!ItemStack.areItemsEqual(heldItem, enchantItem)) {
            advanceStage();
            player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.nomatch"), true);
            enchantItem = ItemStack.EMPTY;
            return;
        }
        Optional<IEnchanterRecipe> enchanterRecipe = world.getRecipeManager().getRecipe(UCRecipes.ENCHANTER_TYPE, wrap(), world);
        if (!enchanterRecipe.isPresent()) {
            advanceStage();
            player.sendStatusMessage(new TranslationTextComponent("uniquecrops.enchanting.unknownrecipe"), true);
        }
        if (enchanterRecipe.isPresent()) {
            IEnchanterRecipe recipe = enchanterRecipe.get();
            recipe.applyEnchantment(heldItem);
            this.clearInv();
            advanceStage();
            world.playEvent(2004, getPos().add(0, 0.7, 0), 0);
        }
        enchantItem = ItemStack.EMPTY;
    }

    public void loopEffects() {

        for (int i = 0; i < ENCHPOS.length; i++) {
            BlockPos loopPos = pos.add(ENCHPOS[i]);
            BlockState loopState = world.getBlockState(loopPos);
            if (loopState.getBlock() == UCBlocks.HEXIS_CROP.get()) {
                int size = 4;
                for (int j = 0; j < size; ++j) {
                    for (int k = 0; k < size; ++k) {
                        double x = ((double)j + 0.5D) / 4.0D;
                        double z = ((double)k + 0.5D) / 4.0D;

                        world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, loopState), (double)loopPos.getX() + x, (double)loopPos.getY() + loopState.getShape(world, loopPos).getEnd(Direction.Axis.Y), (double)loopPos.getZ() + z, ((double)this.getPos().getX() - loopPos.getX()) / 8, 0, ((double)this.getPos().getZ() - loopPos.getZ()) / 8);
                    }
                }
            }
        }
    }

    public IItemHandler getInventory() {

        return this.inv;
    }

    private void clearInv() {

        for (int i = 0; i < inv.getSlots(); i++) {
            inv.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public Stage getStage() {

        return this.stage;
    }

    public int getEnchantingTicks() {

        return this.enchantingTicks;
    }

    public void advanceStage() {

        int mod = Math.floorMod(stage.ordinal() + 1, Stage.values().length);
        stage = Stage.values()[mod];
        this.enchantingTicks = 0;
        this.markBlockForUpdate();
        this.markDirty();
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.put("inventory", inv.serializeNBT());
        tag.putInt(UCStrings.TAG_ENCHANTSTAGE, stage.ordinal());
        if (enchanterId != null)
            tag.putString("UC:targetEnchanter", enchanterId.toString());
        else
            tag.remove("UC:targetEnchanter");
        tag.putInt(UCStrings.TAG_ENCHANT_TIMER, this.enchantingTicks);
        tag.putInt(UCStrings.TAG_ENCHANT_COST, this.enchantmentCost);

        if (!enchantItem.isEmpty())
            tag.put("enchItem", enchantItem.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        inv.deserializeNBT(tag.getCompound("inventory"));
        stage = Stage.values()[tag.getInt(UCStrings.TAG_ENCHANTSTAGE)];
        if (tag.contains("UC:targetEnchanter"))
            enchanterId = UUID.fromString(tag.getString("UC:targetEnchanter"));
        enchantingTicks = tag.getInt(UCStrings.TAG_ENCHANT_TIMER);
        enchantmentCost = tag.getInt(UCStrings.TAG_ENCHANT_COST);

        if (tag.contains("enchItem"))
            ItemStack.read(tag.getCompound("enchItem"));
    }

    public enum Stage {

        IDLE,
        PREPARE {
            @Override
            public void advance(TileFascino tile) {

                if (tile.enchantingTicks >= 20)
                    tile.advanceStage();
            }
        },
        ENCHANT {
            @Override
            public void advance(TileFascino tile) {

                if (tile.enchantingTicks % 4 == 0)
                    tile.loopEffects();
                if (tile.enchantingTicks % 20 == 0)
                    tile.advanceEnchanting();
            }
        },
        STOP {
            @Override
            public void advance(TileFascino tile) {

                tile.enchanterId = null;
                tile.advanceStage();
            }
        };

        public void advance(TileFascino tile) {}
    }
}
