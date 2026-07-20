package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemRibArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemRibArmorTrimSmithingTemplate() {
        super(RIB_ARMOR_TRIM_SMITHING_TEMPLATE, "Rib Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.RIB_ARMOR_TRIM;
    }
}
