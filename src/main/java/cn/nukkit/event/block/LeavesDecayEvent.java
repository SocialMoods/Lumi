package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event called before checking nearby logs or making leaves decay.
 * @author MagicDroidX
 */
public class LeavesDecayEvent extends BlockEvent implements Cancellable {

    /**
     * Event for leaves decaying / disappearing.
     * @param block Leaves block.
     */
    public LeavesDecayEvent(Block block) {
        super(block);
    }
}
