package de.gruppe.plugin.manhunt.menusystem.menu;

import de.gruppe.plugin.manhunt.handlers.CompassHandler;
import de.gruppe.plugin.manhunt.menusystem.AbstractPaginatedMenu;
import de.gruppe.plugin.manhunt.menusystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ManhuntFollowSelectMenu extends AbstractPaginatedMenu {
    public ManhuntFollowSelectMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Player Select";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        Player player;
        if (event.getWhoClicked() instanceof Player) {
            player = (Player) event.getWhoClicked();

            if (event.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                String followPlayerName = event.getCurrentItem().getItemMeta().getDisplayName();

                Player followPlayer = Bukkit.getPlayer(followPlayerName);
                Player userPlayer = (Player) event.getWhoClicked();

                if (CompassHandler.containsUsertTarget(userPlayer.getUniqueId())) {
                    CompassHandler.setUserTarget(userPlayer.getUniqueId(), followPlayer.getUniqueId());
                } else {
                    CompassHandler.addUserTarget(userPlayer.getUniqueId(), followPlayer.getUniqueId());
                }
                event.setCancelled(true);
                userPlayer.closeInventory();
            } else {
                String itemDisplayName = event.getCurrentItem().getItemMeta().getDisplayName();

                if (itemDisplayName.equalsIgnoreCase(ChatColor.GREEN + "Previous Page")) {
                    event.setCancelled(true);
                } else if (itemDisplayName.equalsIgnoreCase(ChatColor.GREEN + "Next Page")) {
                    event.setCancelled(true);

                } else if (itemDisplayName.equalsIgnoreCase(ChatColor.RED + "Close")) {
                    event.setCancelled(true);
                    Player p = (Player) event.getWhoClicked();
                    p.closeInventory();

                }
            }

            player.updateInventory();
        }

    }

    @Override
    public void setMenuItems() {

        addMenuBorder();

        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        if(players.isEmpty() || players == null)
        {
            return;
        }

        for (int i = 0; i < getMaxItemOnPage(); i++) {

            setIndex(getMaxItemOnPage() * getPage() + i);

            if (getIndex() >= players.size())
            {
                break;
            }
            if (players.get(getIndex()) != null)
            {
                ItemStack playerHead = new ItemStack(Material.DIAMOND_SWORD, 1);
                ItemMeta playerHeadMeta = playerHead.getItemMeta();
                playerHeadMeta.setDisplayName(players.get(getIndex()).getName());
                playerHead.setItemMeta(playerHeadMeta);

                Inventory inventory = getInventory();
                inventory.addItem(playerHead);

            }

        }

    }
}
