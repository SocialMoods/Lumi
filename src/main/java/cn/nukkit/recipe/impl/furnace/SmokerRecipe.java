package cn.nukkit.recipe.impl.furnace;

import cn.nukkit.item.Item;
import cn.nukkit.recipe.RecipeType;
import cn.nukkit.recipe.descriptor.ItemDescriptor;

public class SmokerRecipe extends FurnaceRecipe {

    public SmokerRecipe(Item result, ItemDescriptor ingredient) {
        super(result, ingredient);
    }

    public SmokerRecipe(String recipeId, Item result, ItemDescriptor ingredient) {
        super(recipeId, result, ingredient);
    }

    @Override
    public RecipeType getType() {
        return RecipeType.SMOKER;
    }
}
