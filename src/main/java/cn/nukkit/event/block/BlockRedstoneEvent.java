package cn.nukkit.event.block;

import cn.nukkit.block.Block;

/**
 * Event for Redstone Block.
 * @author CreeperFace on 12.5.2017.
 */
public class BlockRedstoneEvent extends BlockEvent {

    private final int oldPower;
    private final int newPower;

    /**
     * Event called on redstone change. E.g Redstone power.
     * @param block Block that is affected.
     * @param oldPower Old power of the block.
     * @param newPower New power of the block.
     */
    public BlockRedstoneEvent(Block block, int oldPower, int newPower) {
        super(block);
        this.oldPower = oldPower;
        this.newPower = newPower;
    }

    public int getOldPower() {
        return oldPower;
    }

    public int getNewPower() {
        return newPower;
    }
}
