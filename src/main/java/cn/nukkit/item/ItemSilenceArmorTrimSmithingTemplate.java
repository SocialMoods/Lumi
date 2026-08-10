package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemSilenceArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemSilenceArmorTrimSmithingTemplate() {
        super(SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, "Silence Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.SILENCE_ARMOR_TRIM;
    }
}
