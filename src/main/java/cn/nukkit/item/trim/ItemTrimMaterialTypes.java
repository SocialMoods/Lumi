package cn.nukkit.item.trim;

import cn.nukkit.network.protocol.types.TrimMaterial;

public interface ItemTrimMaterialTypes {
    TrimMaterial MATERIAL_QUARTZ = new TrimMaterial("quartz", "§h", "minecraft:quartz");
    TrimMaterial MATERIAL_IRON = new TrimMaterial("iron", "§i", "minecraft:iron_ingot");
    TrimMaterial MATERIAL_NETHERITE = new TrimMaterial("netherite", "§j", "minecraft:netherite_ingot");
    TrimMaterial MATERIAL_REDSTONE = new TrimMaterial("redstone", "§m", "minecraft:redstone");
    TrimMaterial MATERIAL_COPPER = new TrimMaterial("copper", "§n", "minecraft:copper_ingot");
    TrimMaterial MATERIAL_GOLD = new TrimMaterial("gold", "§p", "minecraft:gold_ingot");
    TrimMaterial MATERIAL_EMERALD = new TrimMaterial("emerald", "§q", "minecraft:emerald");
    TrimMaterial MATERIAL_DIAMOND = new TrimMaterial("diamond", "§s", "minecraft:diamond");
    TrimMaterial MATERIAL_LAPIS = new TrimMaterial("lapis", "§t", "minecraft:lapis_lazuli");
    TrimMaterial MATERIAL_AMETHYST = new TrimMaterial("amethyst", "§u", "minecraft:amethyst_shard");
    TrimMaterial MATERIAL_RESIN = new TrimMaterial("resin", "§v", "minecraft:resin_brick");
}
