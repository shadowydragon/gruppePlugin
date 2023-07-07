package de.gruppe.plugin.cojoin.cojoinmenusystem;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import de.gruppe.plugin.menusystem.AbstractMenu;
import de.gruppe.plugin.menusystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoJoinControllerRoleMenu extends AbstractMenu {
    //private final Material CONTROLLERICONMATERIAL = Material.EMERALD;
    public CoJoinControllerRoleMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Controller Role";
    }

    public String getMenuName(CoJoinController controller) {
        return controller.getControllerName();
    }

    @Override
    public int getSlots() {
        return 9 * 2;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        ItemStack clickedItem = event.getCurrentItem();

        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromName(Objects.requireNonNull(event.getClickedInventory()).getType().name());

        assert clickedItem != null;
        assert controller != null;
        controller.addCoJoinPlayerRole((Player) event.getWhoClicked(), CoJoinRole.valueOf(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName()));

        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {

    }

    public void open(CoJoinController controller) {

        PlayerMenuUtility playerMenuUtility = getPlayerMenuUtility();

        Inventory inventory = Bukkit.createInventory(this, getSlots(), getMenuName(controller));

        setInventory(inventory);

        this.setMenuItems(controller);

        playerMenuUtility.getOwner().openInventory(inventory);

    }

    public void setMenuItems(CoJoinController controller) {
        if (controller != null) {

            ItemStack roleIcons = new ItemStack(Material.BARRIER);
            ItemMeta roleIconMeta = roleIcons.getItemMeta();
            List<String> lore = new ArrayList<>();

            Inventory inventory = getInventory();

            controller.getPlayerInController().forEach((role, player) -> {
                roleIcons.setType(Material.EMERALD);
                assert roleIconMeta != null;
                roleIconMeta.setDisplayName(role.name());
                lore.add(player != null ? player.getName() : "None");
                roleIconMeta.setLore(lore);
                lore.clear();

                roleIcons.setItemMeta(roleIconMeta);
                inventory.addItem(roleIcons);

            });

        }
    }

    @Override
    public void setMenuItems(Player player) {

    }
}
