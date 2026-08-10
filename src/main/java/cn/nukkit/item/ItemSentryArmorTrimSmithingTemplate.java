package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemSentryArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemSentryArmorTrimSmithingTemplate() {
        super(SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, "Sentry Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.SENTRY_ARMOR_TRIM;
    }
}
