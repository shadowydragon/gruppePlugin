package de.gruppe.plugin.manhunt.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public class TargetCompass
{
    public static ItemStack createCreate()
    {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = compass.getItemMeta();

        compassMeta.setUnbreakable(true);
        compassMeta.setDisplayName(ChatColor.RED + "Targeter");

        List<String> compassLore = new LinkedList<>();
        compassLore.add("Dieser Compass kann");
        compassLore.add("dir die Position");
        compassLore.add("eines beliebigen");
        compassLore.add("Spielers anzeigen");
        compassMeta.setLore(compassLore);


        compass.setItemMeta(compassMeta);


        return compass;
    }
}
