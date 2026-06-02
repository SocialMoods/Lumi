package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event for Block spread.
 * @author MagicDroidX
 */
public class BlockSpreadEvent extends BlockFormEvent implements Cancellable {

    private final Block source;

    /**
     * Event for block spread, such as grass or mycelium.
     * @param block Block that is being spread.
     * @param source The source block.
     * @param newState New state of spread block.
     */
    public BlockSpreadEvent(Block block, Block source, Block newState) {
        super(block, newState);
        this.source = source;
    }

    public Block getSource() {
        return source;
    }
}
