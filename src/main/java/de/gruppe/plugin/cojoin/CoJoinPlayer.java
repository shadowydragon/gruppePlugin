package de.gruppe.plugin.cojoin;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CoJoinPlayer {

    private final HashMap<CoJoinRole, Player> playerHashMap = new HashMap<>();
    private final String controlerName;

    public CoJoinPlayer(String controlerName) {
        this.controlerName = controlerName;
        for (CoJoinRole value : CoJoinRole.values()) {
            playerHashMap.put(value, null);
        }
    }

    public HashMap<CoJoinRole, Player> getPlayerHashMap() {
        return playerHashMap;
    }

    public void addCoJoinPlayerRole(Player player, CoJoinRole role)
    {
        playerHashMap.put(role, player);
    }

    public String getControlerName() {
        return controlerName;
    }

    //you get all roles that not set for this controller
    //if its full then you get null
    public Set<CoJoinRole> getEmptyRoles()
    {
        Set<CoJoinRole> emptyRoles = new HashSet<>();

        for (CoJoinRole value : CoJoinRole.values()) {
            if (playerHashMap.get(value) == null)
            {
                emptyRoles.add(value);
            }
        }
        if (emptyRoles.isEmpty())
        {
            return null;
        }
        return emptyRoles;

    }
}
