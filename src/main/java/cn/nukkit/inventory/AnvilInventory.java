package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.event.inventory.AnvilInventoryChangeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Position;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class AnvilInventory extends FakeBlockUIComponent {

    public static final int ANVIL_INPUT_UI_SLOT = 1;
    public static final int ANVIL_MATERIAL_UI_SLOT = 2;
    public static final int ANVIL_OUTPUT_UI_SLOT = CREATED_ITEM_OUTPUT_UI_SLOT;

    public static final int TARGET = 0;
    public static final int SACRIFICE = 1;
    public static final int RESULT = ANVIL_OUTPUT_UI_SLOT - 1; //1: offset;

    @Getter
    private Item clientInputItem = Item.AIR_ITEM.clone();
    @Getter
    private Item clientMaterialItem = Item.AIR_ITEM.clone();

    private int cost;

    public AnvilInventory(PlayerUIInventory playerUI, Position position) {
        super(playerUI, InventoryType.ANVIL, 1, position);
    }

    /**
     * Calls then client changes slot (only clientside)
     * @param slot 0 (target) or 1 (material)
     * @param oldItem old item
     * @param newItem new item
     */
    public void onSlotChange(int slot, Item oldItem, Item newItem) {
        if (slot == TARGET) {
            this.clientInputItem = newItem.clone();
        } else if (slot == SACRIFICE) {
            this.clientMaterialItem = newItem.clone();
        }

        AnvilInventoryChangeEvent event = new AnvilInventoryChangeEvent(this);
        if (!event.call()) {
            this.getViewers().forEach(player -> {
                player.removeAllWindows(false);
                player.sendAllInventories();
            });
        }
    }

    @Override
    public void onClose(Player who) {
        super.onClose(who);
        who.craftingType = Player.CRAFTING_SMALL;
        who.resetCraftingGridType();

        for (int i = 0; i < 2; ++i) {
            this.getHolder().getLevel().dropItem(this.getHolder().add(0.5, 0.5, 0.5), this.getItem(i));
            this.clear(i);
        }
    }

    @Override
    public void onOpen(Player who) {
        super.onOpen(who);
        who.craftingType = Player.ANVIL_WINDOW_ID;
    }

    public Item getInputSlot() {
        return this.getItem(TARGET);
    }

    public Item getMaterialSlot() {
        return this.getItem(SACRIFICE);
    }

    public Item getOutputSlot() {
        return this.getItem(RESULT);
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
