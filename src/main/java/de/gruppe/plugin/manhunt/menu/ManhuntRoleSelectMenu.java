package de.gruppe.plugin.manhunt.menu;

import de.gruppe.plugin.manhunt.ManhuntPlayerRoleUtil;
import de.gruppe.plugin.manhunt.ManhuntRoles;
import de.gruppe.plugin.menusystem.AbstractMenu;
import de.gruppe.plugin.menusystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class ManhuntRoleSelectMenu extends AbstractMenu {

    //Generate the Constructor for the Menu
    public ManhuntRoleSelectMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    //Set the name of the titel from the Menu
    @Override
    public String getMenuName() {
        return ChatColor.GREEN + " " + ChatColor.BOLD + "Role Select";
    }

    //Set the count of slots for the menu
    //it's a multiplier of 9
    @Override
    public int getSlots() {
        return 9;
    }

    //The method what will happen when you click in the inventory
    @Override
    public void handleMenu(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem()).getItemMeta()).getDisplayName().equalsIgnoreCase("hunter")) {
            ManhuntPlayerRoleUtil.changeRole(player, ManhuntRoles.HUNTER);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("none")) {
            ManhuntPlayerRoleUtil.changeRole(player, ManhuntRoles.NONE);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("speedrunner")) {
            ManhuntPlayerRoleUtil.changeRole(player, ManhuntRoles.SPEEDRUNNER);
        }

        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        setMenuItems(null);
    }

    @Override
    public void setMenuItems(Player player) {

        ItemStack noneRole = new ItemStack(Material.BARRIER, 1);
        ItemMeta noneMaterial = noneRole.getItemMeta();
        Objects.requireNonNull(noneMaterial).setDisplayName("None");
        noneRole.setItemMeta(noneMaterial);

        ItemStack hunterRole = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta huntMeta = hunterRole.getItemMeta();
        Objects.requireNonNull(huntMeta).setDisplayName("Hunter");
        hunterRole.setItemMeta(huntMeta);

        ItemStack speedrunnerRole = new ItemStack(Material.NETHERITE_BOOTS, 1);
        ItemMeta speedrunnerMeta = speedrunnerRole.getItemMeta();
        Objects.requireNonNull(speedrunnerMeta).setDisplayName("Speedrunner");
        speedrunnerRole.setItemMeta(speedrunnerMeta);

        Inventory inventory = getInventory();
        inventory.setItem(0, noneRole);
        inventory.setItem(4, hunterRole);
        inventory.setItem(8, speedrunnerRole);
    }
}
