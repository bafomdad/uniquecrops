package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.api.IEnchanterRecipe;
import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCTiles;
import com.bafomdad.uniquecrops.items.StaffWildwoodItem;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.*;

public class TileFascino extends BaseTileUC {

    private final BlockPos[] ENCHPOS = new BlockPos[] {
            new BlockPos(0, 0, 3), new BlockPos(0, 0, -3), new BlockPos(3, 0, 0), new BlockPos(-3, 0, 0),
            new BlockPos(2, 0, 2), new BlockPos(-2, 0, -2), new BlockPos(2, 0, -2), new BlockPos(-2, 0, 2)
    };

    public TileFascino(BlockPos pos, BlockState state) {

        super(UCTiles.FASCINO.get(), pos, state);
    }

    private final ItemStackHandler inv = new ItemStackHandler(5) {
        @Override
        public int getSlotLimit(int slot) {

            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {

            setChanged();
        }
    };

    private ItemStack enchantItem = ItemStack.EMPTY;

    private final int RANGE = 7;
    private Stage stage = Stage.IDLE;
    private UUID enchanterId;
    private int enchantingTicks = 0;
    private int enchantmentCost = 7;
    private boolean showMissingCrops;

    public void tickServer() {

        if (showMissingCrops)
            loopMissingCrops();

        if (stage == Stage.IDLE) return;

        enchantingTicks++;
        stage.advance(this);
    }

    public void loopMissingCrops() {

        for (int i = 0; i < ENCHPOS.length; i++) {
            BlockPos loopPos = worldPosition.offset(ENCHPOS[i]);
            BlockState loopState = level.getBlockState(loopPos);
            if (loopState.getBlock() != UCBlocks.HEXIS_CROP.get()) {
                UCPacketHandler.sendToNearbyPlayers(level, loopPos, new PacketUCEffect(EnumParticle.SMOKE, loopPos.getX() - 0.5, loopPos.getY() + 0.1, loopPos.getZ() - 0.5, 4));
            }
        }
    }

    public void checkEnchants(Player player, ItemStack staff) {

            Optional<IEnchanterRecipe> fascinoRecipe = level.getRecipeManager().getRecipeFor(UCItems.ENCHANTER_TYPE, wrap(), level);
            if (!fascinoRecipe.isPresent())
                player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.unknownrecipe"), true);

            fascinoRecipe.ifPresent(recipe -> {
                    ItemStack heldItem = ItemStack.EMPTY;
                    this.showMissingCrops = false;
                    for (ItemStack stack : player.getHandSlots()) {
                        if (!stack.isEmpty() && stack.getItem().isEnchantable(stack) && stack.getItem() != UCItems.WILDWOOD_STAFF.get()) {
                            heldItem = stack;
                            break;
                        }
                    }
                    if (heldItem.isEmpty()) {
                        player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.nothing"), true);
                        return;
                    }
                    if (!recipe.getEnchantment().category.canEnchant(heldItem.getItem())) {
                        player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.unenchantable",heldItem.getDisplayName()), true);
                        return;
                    }
                    if (EnchantmentHelper.getEnchantments(heldItem).containsKey(recipe.getEnchantment())) {
                        player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.enchantmentexists"), true);
                        return;
                    }
                    Map<Enchantment, Integer> enchantSet = EnchantmentHelper.getEnchantments(heldItem);
                    Set<Enchantment> enchantments = enchantSet.keySet();
                    for (Enchantment ench : enchantments) {
                        if (!ench.isCompatibleWith(recipe.getEnchantment())) {
                            player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.incompatible", ench.getDescriptionId()), true);
                            return;
                        }
                    }
                    prepareEnchanting(player, heldItem.getEnchantmentTags().size() + 1, staff, recipe.getCost());
                    enchantItem = heldItem;
                    return;
            });
    }

    private SimpleContainer wrap() {

        SimpleContainer inv = new SimpleContainer(getInventory().getSlots()) {
            @Override
            public int getMaxStackSize() {

                return 1;
            }
        };
        for (int i = 0; i < getInventory().getSlots(); i++)
            inv.setItem(i, getInventory().getStackInSlot(i));

        return inv;
    }

    private void prepareEnchanting(Player player, int enchantmentSize, ItemStack staff, int powerCost) {

        int maxGrowth = 7;
        for (int i = 0; i < ENCHPOS.length; i++) {
            BlockPos loopPos = worldPosition.offset(ENCHPOS[i]);
            BlockState loopState = level.getBlockState(loopPos);
            if (loopState.getBlock() == UCBlocks.HEXIS_CROP.get()) {
                int age = loopState.getValue(BaseCropsBlock.AGE);
                if (age < maxGrowth)
                    maxGrowth = age;
            }
            else {
                player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.missingcrops"), true);
                this.showMissingCrops = true;
                enchantItem = ItemStack.EMPTY;
                return;
            }
        }
        if (maxGrowth < enchantmentSize) {
            player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.cropgrowth",enchantmentSize), true);
            enchantItem = ItemStack.EMPTY;
            return;
        }
        if (!StaffWildwoodItem.adjustPower(staff, powerCost)) {
            player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.notenoughpower", powerCost), true);
            enchantItem = ItemStack.EMPTY;
            return;
        }
        stage = Stage.PREPARE;
        enchantmentCost = enchantmentSize;
        enchanterId = player.getUUID();
        this.markBlockForUpdate();
        this.setChanged();
    }

    private Player getEnchanter() {

        List<Player> playerList = level.getEntitiesOfClass(Player.class, new AABB(worldPosition.offset(-RANGE, -1, -RANGE), worldPosition.offset(RANGE, 2, RANGE)));
        for (Player player : playerList) {
            if (player.getUUID().equals(enchanterId)) {
                return player;
            }
        }
        return null;
    }

    public void advanceEnchanting() {

        Player player = getEnchanter();
        if (player == null) {
            advanceStage();
        }
        for (int i = 0; i < ENCHPOS.length; i++) {
            BlockPos loopPos = worldPosition.offset(ENCHPOS[i]);
            BlockState loopState = level.getBlockState(loopPos);
            if (loopState.getBlock() == UCBlocks.HEXIS_CROP.get()) {
                int age = loopState.getValue(BaseCropsBlock.AGE);
                if (age < (7 - enchantmentCost)) {
                    finishEnchanting();
                    break;
                }
                level.levelEvent(2001, loopPos, Block.getId(loopState));
                level.setBlockAndUpdate(loopPos, loopState.setValue(BaseCropsBlock.AGE, Math.max(age - 1, 0)));
            }
            else {
                advanceStage();
                break;
            }
        }
    }

    private void finishEnchanting() {

        if (enchantingTicks < 80) return;

        Player player = getEnchanter();
        if (player == null) {
            advanceStage();
            enchantItem = ItemStack.EMPTY;
            return;
        }
        ItemStack heldItem = ItemStack.EMPTY;
        for (ItemStack stack : player.getHandSlots()) {
            if (!stack.isEmpty() && stack.getItem().isEnchantable(stack) && stack.getItem() != UCItems.WILDWOOD_STAFF.get()) {
                heldItem = stack;
                break;
            }
        }
        if (heldItem.isEmpty()) {
            advanceStage();
            player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.nothing"), true);
            enchantItem = ItemStack.EMPTY;
            return;
        }
        if (!ItemStack.isSame(heldItem, enchantItem)) {
            advanceStage();
            player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.nomatch"), true);
            enchantItem = ItemStack.EMPTY;
            return;
        }
        Optional<IEnchanterRecipe> enchanterRecipe = level.getRecipeManager().getRecipeFor(UCItems.ENCHANTER_TYPE, wrap(), level);
        if (!enchanterRecipe.isPresent()) {
            advanceStage();
            player.displayClientMessage(new TranslatableComponent("uniquecrops.enchanting.unknownrecipe"), true);
        }
        if (enchanterRecipe.isPresent()) {
            IEnchanterRecipe recipe = enchanterRecipe.get();
            recipe.applyEnchantment(heldItem);
            this.clearInv();
            advanceStage();
            level.levelEvent(2004, getBlockPos().offset(0, 0.7, 0), 0);
        }
        enchantItem = ItemStack.EMPTY;
    }

    public void loopEffects() {

        for (int i = 0; i < ENCHPOS.length; i++) {
            BlockPos loopPos = worldPosition.offset(ENCHPOS[i]);
            BlockState loopState = level.getBlockState(loopPos);
            if (loopState.getBlock() == UCBlocks.HEXIS_CROP.get()) {
                int size = 4;
                ((ServerLevel)level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, loopState), loopPos.getX(), (double)loopPos.getY() + loopState.getShape(level, loopPos).max(Direction.Axis.Y), loopPos.getZ(), size, ((double)this.getBlockPos().getX() - loopPos.getX()) / 8, 0, ((double)this.getBlockPos().getZ() - loopPos.getZ()) / 8, 0.25F);
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

    public void advanceStage() {

        int mod = Math.floorMod(stage.ordinal() + 1, Stage.values().length);
        stage = Stage.values()[mod];
        this.enchantingTicks = 0;
        this.markBlockForUpdate();
        this.setChanged();
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

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
    public void readCustomNBT(CompoundTag tag) {

        inv.deserializeNBT(tag.getCompound("inventory"));
        stage = Stage.values()[tag.getInt(UCStrings.TAG_ENCHANTSTAGE)];
        if (tag.contains("UC:targetEnchanter"))
            enchanterId = UUID.fromString(tag.getString("UC:targetEnchanter"));
        enchantingTicks = tag.getInt(UCStrings.TAG_ENCHANT_TIMER);
        enchantmentCost = tag.getInt(UCStrings.TAG_ENCHANT_COST);

        if (tag.contains("enchItem"))
            ItemStack.of(tag.getCompound("enchItem"));
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
