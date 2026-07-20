package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemShaperArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemShaperArmorTrimSmithingTemplate() {
        super(SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, "Shaper Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.SHAPER_ARMOR_TRIM;
    }
}
