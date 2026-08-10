package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemSnoutArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemSnoutArmorTrimSmithingTemplate() {
        super(SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, "Snout Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.SNOUT_ARMOR_TRIM;
    }
}
