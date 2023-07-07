package de.gruppe.plugin.manhunt;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ManhuntUtil {

    public final static String COMPASSNETHER = ChatColor.RED + "Nether Tracker";
    public final static String COMPASSWORLD = ChatColor.RED + "Overworld Tracker";

    public static boolean checkCompassDisplayname(ItemStack compass) {

        if (compass.getType().equals(Material.COMPASS)) {
            String displayname = Objects.requireNonNull(compass.getItemMeta()).getDisplayName();

            if (displayname.equalsIgnoreCase(COMPASSWORLD)) {
                return true;
            } else if (displayname.equalsIgnoreCase(COMPASSNETHER)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
