package de.gruppe.plugin.manhunt.menu;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.manhunt.items.TargetCompass;
import de.gruppe.plugin.menusystem.AbstractMenu;
import de.gruppe.plugin.menusystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ManhuntMainMenu extends AbstractMenu {
    public ManhuntMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.RED + "Manhunt Menu";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        String displayName = Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem()).getItemMeta()).getDisplayName();
        if (displayName.equals(ChatColor.DARK_AQUA + "Close")) {
            event.getWhoClicked().closeInventory();
        } else if (displayName.equals(ChatColor.GREEN + "Locate Player")) {

            if (event.getWhoClicked().getInventory().contains(TargetCompass.createCreate())) {
                event.getWhoClicked().sendMessage("You already own a compass.");
                return;
            }
            if (event.getWhoClicked().getInventory().getItem(8) == null) {

                event.getWhoClicked().getInventory().setItem(8, TargetCompass.createCreate());
            } else {

                event.getWhoClicked().getInventory().addItem(TargetCompass.createCreate());
            }
            event.getWhoClicked().sendMessage("Here you got your Compass.");
        } else if (displayName.equalsIgnoreCase("Select follow target")) {
            ManhuntFollowSelectMenu manhuntFollowSelectMenu = new ManhuntFollowSelectMenu(Main.getPlayerMenuUtility((Player) event.getWhoClicked()));
            manhuntFollowSelectMenu.open(((Player) event.getWhoClicked()).getPlayer());
        } else if (displayName.equalsIgnoreCase("Select Role")) {
            ManhuntRoleSelectMenu manhuntRoleSelectMenu = new ManhuntRoleSelectMenu(Main.getPlayerMenuUtility((Player) event.getWhoClicked()));
            manhuntRoleSelectMenu.open();
        }

    }

    @Override
    public void setMenuItems() {

        ItemStack getCompass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = getCompass.getItemMeta();
        Objects.requireNonNull(compassMeta).setUnbreakable(true);
        compassMeta.setDisplayName(ChatColor.GREEN + "Locate Player");
        List<String> compassLore = new LinkedList<>();
        compassLore.add("Erhalte einen Compass");
        compassMeta.setLore(compassLore);
        getCompass.setItemMeta(compassMeta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        Objects.requireNonNull(closeMeta).setDisplayName(ChatColor.DARK_AQUA + "Close");
        closeMeta.setLore(new ArrayList<>(Collections.singleton("Close the Menu")));
        close.setItemMeta(closeMeta);

        ItemStack playerTarget = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta playerTargetMeta = playerTarget.getItemMeta();
        Objects.requireNonNull(playerTargetMeta).setDisplayName("Select follow target");
        playerTarget.setItemMeta(playerTargetMeta);

        ItemStack selectRole = new ItemStack(Material.EMERALD, 1);
        ItemMeta selectRoleMeta = selectRole.getItemMeta();
        Objects.requireNonNull(selectRoleMeta).setDisplayName("Select Role");
        selectRole.setItemMeta(selectRoleMeta);

        Inventory inventory = this.getInventory();
        inventory.setItem(8, close);
        inventory.setItem(0, getCompass);
        inventory.setItem(1, playerTarget);
        inventory.setItem(2, selectRole);
    }

    @Override
    public void setMenuItems(Player player) {

        ItemStack getCompass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = getCompass.getItemMeta();
        Objects.requireNonNull(compassMeta).setUnbreakable(true);
        compassMeta.setDisplayName(ChatColor.GREEN + "Locate Player");
        List<String> compassLore = new LinkedList<>();
        compassLore.add("Erhalte einen Compass");
        compassMeta.setLore(compassLore);
        getCompass.setItemMeta(compassMeta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        Objects.requireNonNull(closeMeta).setDisplayName(ChatColor.DARK_AQUA + "Close");
        closeMeta.setLore(new ArrayList<>(Collections.singleton("Close the Menu")));
        close.setItemMeta(closeMeta);

        ItemStack playerTarget = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta playerTargetMeta = playerTarget.getItemMeta();
        Objects.requireNonNull(playerTargetMeta).setDisplayName("Select follow target");
        playerTarget.setItemMeta(playerTargetMeta);

        ItemStack selectRole = new ItemStack(Material.EMERALD, 1);
        ItemMeta selectRoleMeta = selectRole.getItemMeta();
        Objects.requireNonNull(selectRoleMeta).setDisplayName("Select Role");
        selectRole.setItemMeta(selectRoleMeta);

        Inventory inventory = this.getInventory();
        inventory.setItem(8, close);
        inventory.setItem(0, getCompass);
        inventory.setItem(1, playerTarget);
        inventory.setItem(2, selectRole);
    }
}
