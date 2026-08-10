package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemHostArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemHostArmorTrimSmithingTemplate() {
        super(HOST_ARMOR_TRIM_SMITHING_TEMPLATE, "Host Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.HOST_ARMOR_TRIM;
    }
}
