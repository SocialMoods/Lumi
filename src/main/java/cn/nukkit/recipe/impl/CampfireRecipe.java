package cn.nukkit.recipe.impl;

import cn.nukkit.item.Item;
import cn.nukkit.recipe.RecipeType;
import cn.nukkit.recipe.SmeltingRecipe;
import cn.nukkit.recipe.descriptor.DefaultDescriptor;
import cn.nukkit.recipe.descriptor.ItemDescriptor;

public class CampfireRecipe implements SmeltingRecipe {

    private final Item output;

    private ItemDescriptor ingredient;

    public CampfireRecipe(Item result, ItemDescriptor ingredient) {
        this.output = result.clone();
        this.ingredient = ingredient;
    }

    public void setInput(Item item) {
        this.ingredient = new DefaultDescriptor(item.clone());
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
        return RecipeType.CAMPFIRE;
    }

    @Override
    public boolean isValidRecipe(int protocol) {
        return true;
    }
}
