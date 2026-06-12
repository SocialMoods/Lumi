package cn.nukkit.event.block;

import cn.nukkit.Player;
import cn.nukkit.blockentity.impl.BlockEntityLectern;
import cn.nukkit.event.Cancellable;

public class LecternPageChangeEvent extends BlockEvent implements Cancellable {
    private final Player player;
    private final BlockEntityLectern lectern;
    private int newRawPage;

    public LecternPageChangeEvent(Player player, BlockEntityLectern lectern, int newPage) {
        super(lectern.getBlock());
        this.player = player;
        this.lectern = lectern;
        this.newRawPage = newPage;
    }

    public BlockEntityLectern getLectern() {
        return lectern;
    }

    public int getLeftPage() {
        return (newRawPage * 2) + 1;
    }

    public int getRightPage() {
        return getLeftPage() + 1;
    }

    public void setLeftPage(int newLeftPage) {
        this.newRawPage = (newLeftPage - 1) / 2;
    }

    public void setRightPage(int newRightPage) {
        this.setLeftPage(newRightPage - 1);
    }

    public int getNewRawPage() {
        return newRawPage;
    }

    public void setNewRawPage(int newRawPage) {
        this.newRawPage = newRawPage;
    }

    public int getMaxPage() {
        return lectern.getTotalPages();
    }

    public Player getPlayer() {
        return player;
    }
}
