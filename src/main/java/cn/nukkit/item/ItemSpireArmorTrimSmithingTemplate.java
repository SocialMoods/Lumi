package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemSpireArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemSpireArmorTrimSmithingTemplate() {
        super(SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, "Spire Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.SPIRE_ARMOR_TRIM;
    }
}
