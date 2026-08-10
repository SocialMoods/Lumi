package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemDuneArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemDuneArmorTrimSmithingTemplate() {
        super(DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, "Dune Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.DUNE_ARMOR_TRIM;
    }
}
