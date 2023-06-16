package de.gruppe.plugin.menusystem;

import jline.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class AbstractMenu implements InventoryHolder {

    private Inventory inventory;

    private PlayerMenuUtility playerMenuUtility;

    public AbstractMenu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent event);

    public abstract void setMenuItems();

    public abstract void setMenuItems(Player player);

    public void open()
    {
        inventory= Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);
    }

    public void open(Player player)
    {
        inventory= Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems(player);

        playerMenuUtility.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
