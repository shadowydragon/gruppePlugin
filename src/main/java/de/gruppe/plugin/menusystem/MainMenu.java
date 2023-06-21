package de.gruppe.plugin.menusystem;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.cojoinmenusystem.CoJoinMainMenu;
import de.gruppe.plugin.manhunt.menu.ManhuntMainMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu extends AbstractMenu{

    private final String manHuntIconDisplayName = ChatColor.RED +"Man Hunt Menu";
    private final Material manHuntIconMaterial = Material.COMPASS;
    private final String coJoinIconDisplayName = ChatColor.GREEN +"Cojoin Menu";
    private final Material coJoinIconMaterial = Material.DIAMOND;
    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Main Menu";
    }

    @Override
    public int getSlots() {
        return 9*5;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(manHuntIconDisplayName))
        {
            ManhuntMainMenu manhuntMainMenu = new ManhuntMainMenu(Main.getPlayerMenuUtility((Player) event.getWhoClicked()));
            manhuntMainMenu.open(((Player) event.getWhoClicked()).getPlayer());
        }
        else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(coJoinIconDisplayName))
        {
            CoJoinMainMenu coJoinMainMenu = new CoJoinMainMenu(Main.getPlayerMenuUtility((Player) event.getWhoClicked()));
            coJoinMainMenu.open();
        }

        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {

        //Generate the Displayed Items
        ItemStack manHuntIcon = new ItemStack(manHuntIconMaterial);
        ItemMeta manHuntIconMeta = manHuntIcon.getItemMeta();

        manHuntIconMeta.setDisplayName(manHuntIconDisplayName);
        manHuntIcon.setItemMeta(manHuntIconMeta);


        ItemStack coJoinIcon = new ItemStack(coJoinIconMaterial);
        ItemMeta coJoinIconMeta = coJoinIcon.getItemMeta();
        coJoinIconMeta.setDisplayName(coJoinIconDisplayName);
        coJoinIcon.setItemMeta(coJoinIconMeta);


        //Fill the Items into the menu
        Inventory menuInventory = getInventory();
        menuInventory.addItem(manHuntIcon);
        menuInventory.addItem(coJoinIcon);

    }

    @Override
    public void setMenuItems(Player player) {

    }
}
