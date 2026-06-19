package cn.nukkit.block;

import cn.nukkit.block.data.BlockColor;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;

public class BlockDoubleSlabCinnabar extends BlockSolidMeta {

    public BlockDoubleSlabCinnabar() {
        this(0);
    }

    public BlockDoubleSlabCinnabar(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return CINNABAR_DOUBLE_SLAB;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public double getResistance() {
        return 30;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    public String getName() {
        return "Double Cinnabar Slab";
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(CINNABAR_SLAB));
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                new ItemBlock(Block.get(CINNABAR_SLAB), 0, 2)
        };
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.RED_BLOCK_COLOR;
    }
}