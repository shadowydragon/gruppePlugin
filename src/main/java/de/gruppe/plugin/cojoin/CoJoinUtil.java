package de.gruppe.plugin.cojoin;

import de.gruppe.plugin.manhunt.ManhuntRoles;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class CoJoinUtil
{
    public static HashMap<CoJoinRole, Player> playerRoleMap = new HashMap<>();
    public static void setCoJoinPlayerRole(Player player, CoJoinRole role)
    {
        playerRoleMap.put(role, player);
    }
    public static void setCoJoinPlayerRole(Player player, String role)
    {
        playerRoleMap.put(CoJoinRole.valueOf(role), player);
    }

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
        else if (checkString.equalsIgnoreCase(CoJoinRole.MOVEMENT.name()))
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
}
