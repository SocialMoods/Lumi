package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event for Block Update
 * @author MagicDroidX
 */
public class BlockUpdateEvent extends BlockEvent implements Cancellable {

    /**
     * Event called on a block being updated.
     * @param block Block updated.
     */
    public BlockUpdateEvent(Block block) {
        super(block);
    }
}
