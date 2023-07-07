package de.gruppe.plugin.menusystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public abstract class AbstractPaginatedMenu extends AbstractMenu {
    private int page = 0;

    //28 empty slots per page
    private int maxItemOnPage = 28;

    private int index = 0;

    public AbstractPaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public void addMenuBorder() {
        ItemStack goLeft = new ItemStack(Material.ARROW, 1);
        ItemMeta goLeftMeta = goLeft.getItemMeta();
        Objects.requireNonNull(goLeftMeta).setDisplayName(ChatColor.GREEN + "Previous Page");
        goLeft.setItemMeta(goLeftMeta);

        ItemStack goRight = new ItemStack(Material.ARROW, 1);
        ItemMeta goRightMeta = goLeft.getItemMeta();
        goRightMeta.setDisplayName(ChatColor.GREEN + "Next Page");
        goRight.setItemMeta(goRightMeta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = goLeft.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Close");
        close.setItemMeta(closeMeta);

        Inventory inventory = getInventory();

        inventory.setItem(0, goLeft);
        inventory.setItem(4, close);
        inventory.setItem(8, goRight);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxItemOnPage() {
        return maxItemOnPage;
    }

    public void setMaxItemOnPage(int maxItemOnPage) {
        this.maxItemOnPage = maxItemOnPage;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
