package de.gruppe.plugin.manhunt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ManhuntUtil {

    public final static String COMPASSNETHER = ChatColor.RED + "Nether Tracker";
    public final static String COMPASSWORLD = ChatColor.RED + "Overworld Tracker";

    public static boolean checkCompassDisplayname(ItemStack compass)
    {
        String displayname = compass.getItemMeta().getDisplayName();

        if (displayname.equalsIgnoreCase(COMPASSWORLD))
        {
            return true;
        }
        else if (displayname.equalsIgnoreCase(COMPASSNETHER))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


}
