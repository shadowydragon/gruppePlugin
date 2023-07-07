package de.gruppe.plugin.manhunt.listener;

import de.gruppe.plugin.menusystem.AbstractMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (event.getCurrentItem() == null) {
                return;
            }

            InventoryHolder inventoryHolder = Objects.requireNonNull(event.getClickedInventory()).getHolder();

            if (inventoryHolder instanceof AbstractMenu menu) {
                menu.handleMenu(event);
                event.setCancelled(true);
            }
        }
    }
}
