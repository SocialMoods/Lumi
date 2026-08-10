package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemTideArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemTideArmorTrimSmithingTemplate() {
        super(TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, "Tide Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.TIDE_ARMOR_TRIM;
    }
}
