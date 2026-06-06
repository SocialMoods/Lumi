package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event for water freezing.
 */
public class WaterFrostEvent extends BlockEvent implements Cancellable {

    /**
     * Event called on water freezing.
     * @param block Block frozen.
     */
    public WaterFrostEvent(Block block) {
        super(block);
    }
}
