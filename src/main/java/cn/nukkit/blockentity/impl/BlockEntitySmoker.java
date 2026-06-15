package cn.nukkit.blockentity.impl;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.recipe.SmeltingRecipe;
import cn.nukkit.registry.RecipeRegistry;
import cn.nukkit.registry.Registries;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class BlockEntitySmoker extends BlockEntityFurnace {

    public BlockEntitySmoker(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected InventoryType getInventoryType() {
        return InventoryType.SMOKER;
    }

    @Override
    protected String getFurnaceName() {
        return "Smoker";
    }

    @Override
    protected String getClientName() {
        return BlockEntity.SMOKER;
    }

    @Override
    protected SmeltingRecipe matchRecipe(int protocol, Item raw) {
        return Registries.RECIPE.matchSmokerRecipe(raw);
    }

    @Override
    protected int getIdleBlockId() {
        return Block.SMOKER;
    }

    @Override
    protected int getBurningBlockId() {
        return Block.LIT_SMOKER;
    }

    @Override
    protected int getSpeedMultiplier() {
        return 2;
    }
}
