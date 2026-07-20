package cn.nukkit.registry;

import cn.nukkit.item.trim.ItemTrimMaterialTypes;
import cn.nukkit.network.protocol.types.TrimMaterial;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class TrimMaterialRegistry implements IRegistry<String, TrimMaterial, TrimMaterial> {
    private final Map<String, TrimMaterial> materials = new HashMap<>();

    @Override
    public void init() {
        register(ItemTrimMaterialTypes.MATERIAL_QUARTZ);
        register(ItemTrimMaterialTypes.MATERIAL_IRON);
        register(ItemTrimMaterialTypes.MATERIAL_NETHERITE);
        register(ItemTrimMaterialTypes.MATERIAL_REDSTONE);
        register(ItemTrimMaterialTypes.MATERIAL_COPPER);
        register(ItemTrimMaterialTypes.MATERIAL_GOLD);
        register(ItemTrimMaterialTypes.MATERIAL_EMERALD);
        register(ItemTrimMaterialTypes.MATERIAL_DIAMOND);
        register(ItemTrimMaterialTypes.MATERIAL_LAPIS);
        register(ItemTrimMaterialTypes.MATERIAL_AMETHYST);
        register(ItemTrimMaterialTypes.MATERIAL_RESIN);
    }

    @Override
    public void register(String key, TrimMaterial value) {
        materials.put(key, value);
    }

    public void register(TrimMaterial value) {
        register(value.materialId(), value);
    }

    @Override
    public TrimMaterial get(String key) {
        return materials.get(key);
    }

    public List<TrimMaterial> getAll() {
        return new ArrayList<>(materials.values());
    }

    @Override
    public void trim() {
    }

    @Override
    public void reload() {
        materials.clear();
        init();
    }
}