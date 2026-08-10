package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemEyeArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemEyeArmorTrimSmithingTemplate() {
        super(EYE_ARMOR_TRIM_SMITHING_TEMPLATE, "Eye Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.EYE_ARMOR_TRIM;
    }
}
