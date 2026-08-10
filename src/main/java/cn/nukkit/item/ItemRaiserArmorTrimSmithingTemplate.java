package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemRaiserArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemRaiserArmorTrimSmithingTemplate() {
        super(RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, "Raiser Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.RAISER_ARMOR_TRIM;
    }
}
