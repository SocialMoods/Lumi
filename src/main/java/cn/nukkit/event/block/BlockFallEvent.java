package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event for Block falling
 */
public class BlockFallEvent extends BlockEvent implements Cancellable {

    /**
     * This event is called when a block is falling.
     * @param block Block that has fallen.
     */
    public BlockFallEvent(Block block) {
        super(block);
    }
}
