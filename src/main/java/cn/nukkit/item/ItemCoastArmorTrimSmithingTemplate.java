package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemCoastArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemCoastArmorTrimSmithingTemplate() {
        super(COAST_ARMOR_TRIM_SMITHING_TEMPLATE, "Coast Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.COAST_ARMOR_TRIM;
    }
}
