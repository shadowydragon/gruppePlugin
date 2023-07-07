package de.gruppe.plugin.cojoin.cojoinmenusystem;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.menusystem.AbstractMenu;
import de.gruppe.plugin.menusystem.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class CoJoinCharacterControllerMenu extends AbstractMenu {
    private final Material CONTROLLERMATERIAL = Material.DIAMOND;

    public CoJoinCharacterControllerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Controller Menu";
    }

    @Override
    public int getSlots() {
        return 9 * 3;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        ItemStack clickedItem = event.getCurrentItem();

        CoJoinControllerRoleMenu coJoinControllerRoleMenu = new CoJoinControllerRoleMenu(Main.getPlayerMenuUtility((Player) event.getWhoClicked()));

        assert clickedItem != null;
        coJoinControllerRoleMenu.open(CoJoinControllerPlayerList.getControllerFromName(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName()));

        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        ItemStack controller = new ItemStack(CONTROLLERMATERIAL);
        ItemMeta controllerMeta = controller.getItemMeta();

        Inventory menu = getInventory();

        if (!CoJoinControllerPlayerList.getCoJoinPlayerControllers().isEmpty()) {
            for (CoJoinController coJoinPlayerController : CoJoinControllerPlayerList.getCoJoinPlayerControllers()) {
                assert controllerMeta != null;
                controllerMeta.setDisplayName(coJoinPlayerController.getControllerName());
                controller.setItemMeta(controllerMeta);

                menu.addItem(controller);
            }
        }

    }

    @Override
    public void setMenuItems(Player player) {
        setMenuItems();
    }
}
