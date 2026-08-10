package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemWardArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemWardArmorTrimSmithingTemplate() {
        super(WARD_ARMOR_TRIM_SMITHING_TEMPLATE, "Ward Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.WARD_ARMOR_TRIM;
    }
}
