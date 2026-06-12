package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseDialog;
import cn.nukkit.form.window.FormWindowDialog;

public class PlayerDialogRespondedEvent extends PlayerEvent {

    protected FormWindowDialog dialog;

    protected FormResponseDialog response;

    public PlayerDialogRespondedEvent(Player player, FormWindowDialog dialog, FormResponseDialog response) {
        this.dialog = dialog;
        this.response = response;
    }

    public FormWindowDialog getDialog() {
        return dialog;
    }

    public FormResponseDialog getResponse() {
        return response;
    }
}
