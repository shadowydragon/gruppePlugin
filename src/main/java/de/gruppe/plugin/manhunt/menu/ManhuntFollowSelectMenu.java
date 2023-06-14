package de.gruppe.plugin.manhunt.menu;

import de.gruppe.plugin.manhunt.ManhuntPlayerRoleUtil;
import de.gruppe.plugin.manhunt.ManhuntRoles;
import de.gruppe.plugin.manhunt.handlers.CompassHandler;
import de.gruppe.plugin.menusystem.AbstractPaginatedMenu;
import de.gruppe.plugin.menusystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


import java.util.*;

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

        System.out.println("xx");



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

                List<String> playerHeadLore = new LinkedList<>();
                if (players.get(getIndex()).getPersistentDataContainer().get(new NamespacedKey(players.get(getIndex()).getUniqueId().toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING).equalsIgnoreCase("hunter"))
                {
                    playerHeadLore.add("Rolle: " + ChatColor.RED + "" + ChatColor.BOLD + "HUNTER");
                }
                else if (players.get(getIndex()).getPersistentDataContainer().get(new NamespacedKey(players.get(getIndex()).getUniqueId().toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING).equalsIgnoreCase("speedrunner"))
                {
                    playerHeadLore.add("Rolle: " + ChatColor.GREEN + "" + ChatColor.BOLD + "SPEEDRUNNER");
                }
                else if (players.get(getIndex()).getPersistentDataContainer().get(new NamespacedKey(players.get(getIndex()).getUniqueId().toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING).equalsIgnoreCase("none"))
                {
                    playerHeadLore.add("Rolle: " + ChatColor.BLUE + "NONE");
                }

                playerHeadMeta.setLore(playerHeadLore);


                playerHead.setItemMeta(playerHeadMeta);

                Inventory inventory = getInventory();

                inventory.addItem(playerHead);
            }

        }

    }

    @Override
    public void setMenuItems(Player player) {

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

                List<String> playerHeadLore = new LinkedList<>();
                if (players.get(getIndex()).getPersistentDataContainer().get(new NamespacedKey(players.get(getIndex()).getUniqueId().toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING).equalsIgnoreCase("hunter"))
                {
                    playerHeadLore.add("Rolle: " + ChatColor.RED + "" + ChatColor.BOLD + "HUNTER");
                }
                else if (players.get(getIndex()).getPersistentDataContainer().get(new NamespacedKey(players.get(getIndex()).getUniqueId().toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING).equalsIgnoreCase("speedrunner"))
                {
                    playerHeadLore.add("Rolle: " + ChatColor.GREEN + "" + ChatColor.BOLD + "SPEEDRUNNER");
                }
                else if (players.get(getIndex()).getPersistentDataContainer().get(new NamespacedKey(players.get(getIndex()).getUniqueId().toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING).equalsIgnoreCase("none"))
                {
                    playerHeadLore.add("Rolle: " + ChatColor.BLUE + "NONE");
                }
                playerHeadMeta.setLore(playerHeadLore);

                playerHead.setItemMeta(playerHeadMeta);

                Inventory inventory = getInventory();

                System.out.println("hier bin ich");
                if (ManhuntPlayerRoleUtil.getManhuntRole(player).equalsIgnoreCase("speedrunner"))
                {
                    if (ManhuntPlayerRoleUtil.getManhuntRole(players.get(getIndex())).equalsIgnoreCase("speedrunner"))
                    {
                        inventory.addItem(playerHead);
                    }
                }
                else
                {
                    inventory.addItem(playerHead);
                }
            }
        }
    }
}
