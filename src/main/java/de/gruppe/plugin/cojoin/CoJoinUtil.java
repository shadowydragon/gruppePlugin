package de.gruppe.plugin.cojoin;

import de.gruppe.plugin.manhunt.ManhuntRoles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class CoJoinUtil
{
    //public static HashMap<CoJoinRole, Player> playerRoleMap = new HashMap<>();
    /*public static void setCoJoinPlayerRole(Player player, CoJoinRole role)
    {
        playerRoleMap.put(role, player);
    }
    public static void setCoJoinPlayerRole(Player player, String role)
    {
        playerRoleMap.put(CoJoinRole.valueOf(role), player);
    }*/

    public static boolean checkStringIsRole(String checkString)
    {
        if (checkString.equalsIgnoreCase(CoJoinRole.BREAK.name()))
        {
            return true;
        }
        else if (checkString.equalsIgnoreCase(CoJoinRole.ATTACK.name()))
        {
            return true;
        }
        else if (checkString.equalsIgnoreCase(CoJoinRole.MOVEMENT_WALK.name()))
        {
            return true;
        }
        else if (checkString.equalsIgnoreCase(CoJoinRole.MOVEMENT_LOOKDIRECTION.name()))
        {
            return true;
        }
        else if (checkString.equalsIgnoreCase(CoJoinRole.INVENTORY.name()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void updatePlayerLocations(Set<Player> playerSet, Location location)
    {
        for (Player player : playerSet) {
            if (player == null)
            {
                continue;
            }
            player.teleport(location);
        }
    }

    public static void updatePlayerLocations(Set<Player> playerSet, Location location, Player whoMoved)
    {
        for (Player player : playerSet) {
            if (player == null || (player.equals(whoMoved)))
            {
                continue;
            }
            //System.out.println("tele: " + player.getName());
            player.teleport(whoMoved);
            System.out.println(player.getName() + " Collidable: " + player.isCollidable());
            System.out.println(whoMoved.getName() + " Collidable: " + whoMoved.isCollidable());
        }
    }

}
