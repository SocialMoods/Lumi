package cn.nukkit.registry;

import cn.nukkit.item.trim.ItemTrimPatternTypes;
import cn.nukkit.network.protocol.types.TrimPattern;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class TrimPatternRegistry implements IRegistry<String, TrimPattern, TrimPattern> {
    private final Map<String, TrimPattern> patterns = new HashMap<>();

    @Override
    public void init() {
        register(ItemTrimPatternTypes.WARD_ARMOR_TRIM);
        register(ItemTrimPatternTypes.SENTRY_ARMOR_TRIM);
        register(ItemTrimPatternTypes.SNOUT_ARMOR_TRIM);
        register(ItemTrimPatternTypes.DUNE_ARMOR_TRIM);
        register(ItemTrimPatternTypes.SPIRE_ARMOR_TRIM);
        register(ItemTrimPatternTypes.TIDE_ARMOR_TRIM);
        register(ItemTrimPatternTypes.WILD_ARMOR_TRIM);
        register(ItemTrimPatternTypes.RIB_ARMOR_TRIM);
        register(ItemTrimPatternTypes.COAST_ARMOR_TRIM);
        register(ItemTrimPatternTypes.SHAPER_ARMOR_TRIM);
        register(ItemTrimPatternTypes.EYE_ARMOR_TRIM);
        register(ItemTrimPatternTypes.VEX_ARMOR_TRIM);
        register(ItemTrimPatternTypes.SILENCE_ARMOR_TRIM);
        register(ItemTrimPatternTypes.WAYFINDER_ARMOR_TRIM);
        register(ItemTrimPatternTypes.RAISER_ARMOR_TRIM);
        register(ItemTrimPatternTypes.HOST_ARMOR_TRIM);
        register(ItemTrimPatternTypes.FLOW_ARMOR_TRIM);
        register(ItemTrimPatternTypes.BOLT_ARMOR_TRIM);
    }

    @Override
    public void register(String key, TrimPattern value) {
        patterns.put(key, value);
    }

    public void register(TrimPattern value) {
        register(value.patternId(), value);
    }

    @Override
    public TrimPattern get(String key) {
        return patterns.get(key);
    }

    public List<TrimPattern> getAll() {
        return new ArrayList<>(patterns.values());
    }

    @Override
    public void trim() {
    }

    @Override
    public void reload() {
        patterns.clear();
        init();
    }
}