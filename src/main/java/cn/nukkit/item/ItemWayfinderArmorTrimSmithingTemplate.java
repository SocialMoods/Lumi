package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemWayfinderArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemWayfinderArmorTrimSmithingTemplate() {
        super(WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, "Wayfinder Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.WAYFINDER_ARMOR_TRIM;
    }
}
