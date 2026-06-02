package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event for Block growth.
 * @author MagicDroidX
 */
public class BlockGrowEvent extends BlockEvent implements Cancellable {

    private final Block newState;

    /**
     * Called on block grow.
     * @param block Block affected by event E.g Vine.
     * @param newState New state of the affected block.
     */
    public BlockGrowEvent(Block block, Block newState) {
        super(block);
        this.newState = newState;
    }

    public Block getNewState() {
        return newState;
    }
}
