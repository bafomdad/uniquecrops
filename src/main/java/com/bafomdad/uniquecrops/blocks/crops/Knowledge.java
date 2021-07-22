package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Knowledge extends BaseCropsBlock {

    private static final Pattern PAT = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final String BOOKMARK = "UC:tagBookmark";

    public Knowledge() {

        super(UCItems.BOOK_DISCOUNT, UCItems.KNOWLEDGE_SEED);
        setBonemealable(false);
        setIgnoreGrowthRestrictions(true);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (this.canIgnoreGrowthRestrictions(world, pos)) {
            super.randomTick(state, world, pos, rand);
            return;
        }
        if (!this.isMaxAge(state) || world.isRemote) return;

        int growStages = consumeKnowledge(world, pos);
        if (growStages > 0)
            world.setBlockState(pos, this.withAge(Math.min(getAge(state) + growStages, getMaxAge())), 2);
    }

    private int consumeKnowledge(World world, BlockPos pos) {

        AtomicInteger result = new AtomicInteger();
        Iterable<BlockPos> getBox = BlockPos.getAllInBoxMutable(pos.add(-4, -2, -4), pos.add(4, 2, 4));
        Iterator it = getBox.iterator();
        while (it.hasNext()) {
            BlockPos posit = (BlockPos)it.next();
            BlockState loopState = world.getBlockState(posit);
            if (loopState.getBlock().getEnchantPowerBonus(loopState, world, posit) >= 1F) {
                TileEntity te = world.getTileEntity(posit.up());
                if (te != null && te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).isPresent()) {
                    te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(cap -> {
                        for (int i = 0; i < cap.getSlots(); i++) {
                            ItemStack book = cap.getStackInSlot(i);
                            if (!book.isEmpty() && book.getItem() == Items.WRITTEN_BOOK) {
                                CompoundNBT tag = book.getTag();
                                if (WrittenBookItem.validBookTagContents(tag) && !NBTUtils.getBoolean(book, BOOKMARK, false) && WrittenBookItem.getGeneration(book) == 0) {

//                                    int result = 0;
                                    ListNBT tagList = tag.getList("pages", 8);
                                    for (int j = 0; j < tagList.size(); j++) {
                                        String str = tagList.getString(j);
                                        ITextComponent text;
                                        try {
                                            text = ITextComponent.Serializer.getComponentFromJsonLenient(str);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            text = new StringTextComponent(str);
                                        }
                                        String newString = eatSomeVowels(text.getUnformattedComponentText());
                                        ITextComponent newComponent = new StringTextComponent(newString);
                                        tagList.set(j, StringNBT.valueOf(ITextComponent.Serializer.toJson(newComponent)));
                                        result.set(j + 1);
                                    }
                                    tag.put("pages", tagList);
                                    NBTUtils.setBoolean(book, BOOKMARK, true);
                                }
                            }
                        }
                    });
                }
            }
        }
        return result.get();
    }

    private String eatSomeVowels(String str) {

        StringBuilder sb = new StringBuilder(str);
        if (str.length() >= 100 && str.length() <= 512) {
            sb.replace(0, str.length(), str.replaceAll(PAT.pattern(), " "));
            return sb.toString();
        }
        return str;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {

        if (isMaxAge(state)) {
            double x = pos.getX() + rand.nextFloat();
            double y = pos.getY() + 0.5D;
            double z = pos.getZ() + rand.nextFloat();
            worldIn.addParticle(ParticleTypes.ENCHANT, x, y, z, rand.nextGaussian(), rand.nextFloat(), rand.nextGaussian());
        }
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos) {

        return isMaxAge(state) ? 3 : 0;
    }
}
