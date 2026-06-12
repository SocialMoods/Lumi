package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.NukkitRandom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BlockLichen extends BlockTransparentMeta {
    public static final NukkitRandom RANDOM = new NukkitRandom();

    public BlockLichen() {
        this(0);
    }

    public BlockLichen(int meta) {
        super(meta);
    }

    public BlockFace[] getGrowthSides() {
        Stream<BlockFace> returns = Arrays.stream(BlockFace.values()).filter(this::isGrowthToSide);
        return returns.toArray(BlockFace[]::new);
    }

    public void witherAtSide(BlockFace side) {
        if (isGrowthToSide(side)) {
            this.setDamage(this.getDamage() ^ (0b000001 << side.getDUSWNEIndex()));
            getLevel().setBlock(this, this, true, true);
        }
    }

    public boolean isGrowthToSide(BlockFace side) {
        return (this.getDamage() >> side.getDUSWNEIndex() & 0x1) > 0;
    }

    public void growToSide(BlockFace side) {
        if (!isGrowthToSide(side)) {
            this.setDamage(this.getDamage() | (0b000001 << side.getDUSWNEIndex()));
            getLevel().setBlock(this, this, true, true);
        }
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        if (!target.isSolid() && target instanceof BlockLichen) {
            return false;
        }
        int currentMeta = 0;
        if (block instanceof BlockLichen) {
            currentMeta = block.getDamage();
        }

        setDamage(currentMeta | (0b000001 << face.getOpposite().getDUSWNEIndex()));

        if (getDamage() == currentMeta) {
            BlockFace[] sides = BlockFace.values();
            Stream<BlockFace> faceStream = Arrays.stream(sides).filter(side ->
                    block.getSide(side).isSolid(side) && !isGrowthToSide(side)
            );
            Optional<BlockFace> optionalFace = faceStream.findFirst();
            if (optionalFace.isPresent()) {
                growToSide(optionalFace.get());
                return true;
            }

            return false;
        }

        getLevel().setBlock(this, this, true, true);
        return true;
    }

    @Override
    public int onUpdate(int type) {
        for (BlockFace side : BlockFace.values()) {
            final Block support = this.getSide(side);
            if (isGrowthToSide(side) && support != null && !support.isSolid()) {
                this.witherAtSide(side);
            }
        }
        return super.onUpdate(type);
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public double getResistance() {
        return 1;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public int getWaterloggingLevel() {
        return 1;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean sticksToPiston() {
        return false;
    }

    @Override
    public boolean breaksWhenMoved() {
        return true;
    }

    @Override
    public boolean canBeReplaced() {
        return true;
    }

    @Override
    public boolean canBeFlowedInto() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isSolid(BlockFace side) {
        return false;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this, 0);
    }
}
