package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.entity.effect.Effect;
import cn.nukkit.entity.effect.EffectType;
import cn.nukkit.event.player.PlayerItemConsumeEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ProtocolInfo;

public class ItemOminousBottle extends StringItemBase {

    public ItemOminousBottle() {
        super(OMINOUS_BOTTLE, "Ominous Bottle");
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        return true;
    }

    @Override
    public boolean onUse(Player player, int ticksUsed) {
        if (ticksUsed < 10) return false;
        PlayerItemConsumeEvent consumeEvent = new PlayerItemConsumeEvent(player, this);
        if (!consumeEvent.call()) {
            return false;
        }

        if (!player.isCreative()) {
            --this.count;
            player.getInventory().setItemInHand(this);
            player.getInventory().addItem(new ItemGlassBottle());
        }

        Effect effect = Effect.get(EffectType.BAD_OMEN);
        effect.setDuration(20 * 60 * 100); //100m
        effect.setAmplifier(meta);

        player.addEffect(effect);

        return true;
    }

    @Override
    public boolean canRelease() {
        return true;
    }

    @Override
    public boolean isSupportedOn(int protocolId) {
        return protocolId >= ProtocolInfo.v1_21_0;
    }
}
