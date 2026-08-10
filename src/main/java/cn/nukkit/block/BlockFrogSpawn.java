package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.math.BlockFace;

public class BlockFrogSpawn extends BlockFlowable {
    public BlockFrogSpawn() {
        this(0);
    }

    public BlockFrogSpawn(int meta) {
        super(0);
    }

    @Override
    public int getId() {
        return FROG_SPAWN;
    }

    @Override
    public String getName() {
        return "Frogspawn";
    }

    @Override
    public Item[] getDrops(Item item) {
        return Item.EMPTY_ARRAY;
    }

    @Override
    public int getDropExp(Item item) {
        if(item.hasEnchantment(Enchantment.ID_SILK_TOUCH)) return 0;
        return 1;
    }

    @Override
    public boolean canBeFlowedInto() {
        return false;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        if (target instanceof BlockWater) {
            Block up = target.up();
            if (up.getId() == Block.AIR) {
                this.getLevel().setBlock(up, this, true, true);
                return true;
            }
        }

        return false;
    }
}
