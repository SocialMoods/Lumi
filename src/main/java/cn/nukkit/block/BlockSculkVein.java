package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;

public class BlockSculkVein extends BlockLichen {

    public BlockSculkVein() {
        this(0);
    }

    public BlockSculkVein(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return SCULK_VEIN;
    }

    @Override
    public String getName() {
        return "Sculk Vein";
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_HOE;
    }

    @Override
    public double getResistance() {
        return 0.2;
    }

    @Override
    public Item[] getDrops(Item item) {
        Item drop = toItem();
        drop.setCount(getGrowthSides().length);
        if (item.hasEnchantment(Enchantment.ID_SILK_TOUCH)) {
            return new Item[]{
                    drop
            };
        }
        return Item.EMPTY_ARRAY;
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }

    @Override
    public WaterloggingType getWaterloggingType() {
        return WaterloggingType.WHEN_PLACED_IN_WATER;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }
}