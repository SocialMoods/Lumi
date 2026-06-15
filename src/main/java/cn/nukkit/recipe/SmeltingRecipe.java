package cn.nukkit.recipe;

import cn.nukkit.recipe.descriptor.ItemDescriptor;

public interface SmeltingRecipe extends Recipe {
    ItemDescriptor getInput();
}
