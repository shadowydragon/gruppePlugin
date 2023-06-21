package de.gruppe.plugin.cojoin.cojoinmenusystem;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.menusystem.AbstractMenu;
import de.gruppe.plugin.menusystem.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CoJoinMainMenu extends AbstractMenu {
    private String characterControllersIconDisplayName = "Character Controller Menu";
    private Material CHARACTERCONTROLLERMATERIAL = Material.ARMOR_STAND;
    public CoJoinMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Cojoin Menu";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        if (event.getCurrentItem().getType().equals(CHARACTERCONTROLLERMATERIAL))
        {
            CoJoinCharacterControllerMenu coJoinCharacterControllerMenu = new CoJoinCharacterControllerMenu(Main.getPlayerMenuUtility((Player) event.getWhoClicked()));
            coJoinCharacterControllerMenu.open();
        }


        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        ItemStack characterControllers = new ItemStack(CHARACTERCONTROLLERMATERIAL);
        ItemMeta characterControllersMeta = characterControllers.getItemMeta();

        characterControllersMeta.setDisplayName(characterControllersIconDisplayName);

        characterControllers.setItemMeta(characterControllersMeta);


        Inventory menu = getInventory();
        menu.addItem(characterControllers);
    }

    @Override
    public void setMenuItems(Player player) {

    }
}
