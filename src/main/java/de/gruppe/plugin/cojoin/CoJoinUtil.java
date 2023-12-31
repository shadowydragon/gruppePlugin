package de.gruppe.plugin.cojoin;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public class CoJoinUtil {
    //public static HashMap<CoJoinRole, Player> playerRoleMap = new HashMap<>();
    /*public static void setCoJoinPlayerRole(Player player, CoJoinRole role)
    {
        playerRoleMap.put(role, player);
    }
    public static void setCoJoinPlayerRole(Player player, String role)
    {
        playerRoleMap.put(CoJoinRole.valueOf(role), player);
    }*/

    public static boolean checkStringIsRole(String checkString) {
        if (checkString.equalsIgnoreCase(CoJoinRole.BREAK.name())) {
            return true;
        } else if (checkString.equalsIgnoreCase(CoJoinRole.ATTACK.name())) {
            return true;
        } else if (checkString.equalsIgnoreCase(CoJoinRole.MOVEMENT_WALK.name())) {
            return true;
        } else if (checkString.equalsIgnoreCase(CoJoinRole.MOVEMENT_LOOKDIRECTION.name())) {
            return true;
        } else if (checkString.equalsIgnoreCase(CoJoinRole.INVENTORY.name())) {
            return true;
        } else {
            return false;
        }
    }

    public static void updatePlayerLocations(Set<Player> playerSet, Location location) {
        for (Player player : playerSet) {
            if (player == null) {
                continue;
            }
            player.teleport(location);
        }
    }

    public static void updatePlayerLocations(Set<Player> playerSet, Location location, Player whoMoved) {
        for (Player player : playerSet) {
            if (player == null || (player.equals(whoMoved))) {
                continue;
            }
            player.teleport(whoMoved);
        }
    }

}
