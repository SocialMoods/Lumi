package cn.nukkit.block;

import cn.nukkit.block.data.BlockColor;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;

public class BlockDoubleSlabSulfurPolished extends BlockSolidMeta {

    public BlockDoubleSlabSulfurPolished() {
        this(0);
    }

    public BlockDoubleSlabSulfurPolished(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return POLISHED_SULFUR_DOUBLE_SLAB;
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
        return "Double Polished Sulfur Slab";
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(POLISHED_SULFUR_SLAB));
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                new ItemBlock(Block.get(POLISHED_SULFUR_SLAB), 0, 2)
        };
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.YELLOW_BLOCK_COLOR;
    }
}