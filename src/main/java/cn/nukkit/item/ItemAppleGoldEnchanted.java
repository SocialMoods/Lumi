package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.entity.effect.Effect;
import cn.nukkit.entity.effect.EffectType;
import cn.nukkit.math.Vector3;

/**
 * Created by Snake1999 on 2016/1/14.
 * Package cn.nukkit.item in project nukkit.
 */
public class ItemAppleGoldEnchanted extends ItemFood {

    public ItemAppleGoldEnchanted() {
        this(0, 1);
    }

    public ItemAppleGoldEnchanted(Integer meta) {
        this(meta, 1);
    }

    public ItemAppleGoldEnchanted(Integer meta, int count) {
        super(GOLDEN_APPLE_ENCHANTED, meta, count, "Enchanted Apple");
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        return true;
    }

    @Override
    public int getFoodRestore() {
        return 4;
    }

    @Override
    public float getSaturationRestore() {
        return 2.4F;
    }

    @Override
    public boolean onEaten(Player player) {
        player.addEffect(Effect.get(EffectType.ABSORPTION)
                .setAmplifier(3) // 4 lvl
                .setDuration(120 * 20));
        player.addEffect(Effect.get(EffectType.REGENERATION).
                setAmplifier(3). // 4 lvl
                setDuration(30 * 20));
        player.addEffect(Effect.get(EffectType.FIRE_RESISTANCE)
                .setDuration(5 * 60 * 20)); // 1 lvl
        player.addEffect(Effect.get(EffectType.RESISTANCE)
                .setDuration(5 * 60 * 20)); // 1 lvl

        return true;
    }

}
