package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event for forming blocks.
 * @author MagicDroidX
 */
public class BlockFormEvent extends BlockGrowEvent implements Cancellable {
    /**
     * Event for forming blocks.
     * NOTICE: This event isn't meant to be called.
     * @param block Block affected by the event.
     * @param newState New state of the block.
     */
    public BlockFormEvent(Block block, Block newState) {
        super(block, newState);
    }
}
