package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event for Block being burned.
 * @author MagicDroidX
 */
public class BlockBurnEvent extends BlockEvent implements Cancellable {

    /**
     * This event is called when a block is burned.
     * @param block Block that is burned.
     */
    public BlockBurnEvent(Block block) {
        super(block);
    }
}
