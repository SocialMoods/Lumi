package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;

/**
 * @author Glorydark
 */
public class ItemVexArmorTrimSmithingTemplate extends StringItemBase implements ItemTrimPattern {

    public ItemVexArmorTrimSmithingTemplate() {
        super(VEX_ARMOR_TRIM_SMITHING_TEMPLATE, "Vex Armor Trim Smithing Template");
    }

    @Override
    public TrimPattern getPattern() {
        return ItemTrimPatternTypes.VEX_ARMOR_TRIM;
    }
}
