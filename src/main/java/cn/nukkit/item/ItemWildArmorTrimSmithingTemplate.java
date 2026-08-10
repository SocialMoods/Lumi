package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemWildArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemWildArmorTrimSmithingTemplate() {
        super(WILD_ARMOR_TRIM_SMITHING_TEMPLATE, "Wild Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.WILD_ARMOR_TRIM;
    }
}
