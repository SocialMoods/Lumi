package cn.nukkit.recipe.impl.furnace;

import cn.nukkit.item.Item;
import cn.nukkit.recipe.RecipeType;
import cn.nukkit.recipe.SmeltingRecipe;
import cn.nukkit.recipe.descriptor.DefaultDescriptor;
import cn.nukkit.recipe.descriptor.ItemDescriptor;
import cn.nukkit.registry.RecipeRegistry;
import lombok.Getter;

import java.util.UUID;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class FurnaceRecipe implements SmeltingRecipe {

    private String recipeId;

    private UUID id;

    protected final Item output;

    protected ItemDescriptor ingredient;

    @Getter
    private final int networkId;

    public FurnaceRecipe(Item result, ItemDescriptor ingredient) {
        this(null, result, ingredient);
    }

    public FurnaceRecipe(String recipeId, Item result, ItemDescriptor ingredient) {
        this.output = result.clone();
        this.ingredient = ingredient;
        this.recipeId = recipeId;
        this.networkId = ++RecipeRegistry.NEXT_NETWORK_ID;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public ItemDescriptor getInput() {
        return this.ingredient;
    }

    @Override
    public Item getResult() {
        return this.output.clone();
    }

    @Override
    public RecipeType getType() {
        if(ingredient instanceof DefaultDescriptor defaultDescriptor) {
            return defaultDescriptor.getItem().hasMeta() ? RecipeType.FURNACE_DATA : RecipeType.FURNACE;
        }
        return RecipeType.FURNACE;
    }

    public void setInput(ItemDescriptor item) {
        this.ingredient = item;
    }

    public void setId(UUID id) {
        this.id = id;
        if (this.recipeId == null) {
            this.recipeId = this.id.toString();
        }
    }

    @Override
    public boolean isValidRecipe(int protocol) {
        return true;
    }
}
