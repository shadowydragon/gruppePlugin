package de.gruppe.plugin.cojoin;

import de.gruppe.plugin.Util;
import org.bukkit.entity.Player;

import java.util.*;

public class CoJoinController {

    private final HashMap<CoJoinRole, Player> playerInController = new HashMap<>();
    private final String controlerName;

    public CoJoinController(String controlerName) {
        this.controlerName = controlerName;
        for (CoJoinRole value : CoJoinRole.values()) {
            playerInController.put(value, null);
        }
    }

    public HashMap<CoJoinRole, Player> getPlayerInController() {
        return playerInController;
    }

    public void addCoJoinPlayerRole(Player player, CoJoinRole role)
    {
        playerInController.put(role, player);

        //Set that the player cant see the other player for this controller
        if (player == null)
        {
            return;
        }
        Util.hidePlayersFromPlayer(player, getPlayersForController());

        //Check if player has the inventory role for pickup items
        if (role != CoJoinRole.INVENTORY)
        {
            if (playerHasRole(player, CoJoinRole.INVENTORY))
            {
                player.setCanPickupItems(true);
                return;
            }
            player.setCanPickupItems(false);
        }
        else
        {
            player.setCanPickupItems(true);
        }
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
            if (playerInController.get(value) == null)
            {
                if (CoJoinRole.COJOINROLE != value)
                {
                    emptyRoles.add(value);
                }

            }
        }
        if (emptyRoles.isEmpty())
        {
            return null;
        }
        return emptyRoles;

    }

    public Player getPlayerForRole(CoJoinRole role)
    {
        return playerInController.get(role);
    }


    //Returns a list for each player and the role he has as string
    public List<String> getSetRoleAndPlayer()
    {
        List<String> buf = new LinkedList<>();

        for (CoJoinRole value : CoJoinRole.values()) {

            if (playerInController.get(value) != null)
            {
                buf.add(value.name() + ": " + playerInController.get(value).getName());
            }
        }
        if (buf.isEmpty())
        {
            return null;
        }
        return buf;
    }

    //Returns a set for all players who have a role for this controller
    public Set<Player> getPlayersForController()
    {
        Set<Player> playerSet = new HashSet<>();
        for (CoJoinRole value : CoJoinRole.values()) {
            if (playerInController.get(value) == null)
            {
                continue;
            }
            playerSet.add(playerInController.get(value));
        }

        return playerSet;
    }

    public Set<UUID> getPlayersUUIDForController()
    {
        Set<UUID> playerSet = new HashSet<>();
        for (CoJoinRole value : CoJoinRole.values()) {
            if (playerInController.get(value) == null)
            {
                continue;
            }
            playerSet.add(playerInController.get(value).getUniqueId());
        }

        return playerSet;
    }

    //Returns a player list without the given player
    public Set<Player> getPlayersForController(Player player)
    {
        Set<Player> playerSet = new HashSet<>();
        for (CoJoinRole value : CoJoinRole.values()) {
            if (playerInController.get(value) == null || playerInController.get(value).equals(player))
            {
                continue;
            }

            playerSet.add(playerInController.get(value));
        }

        return playerSet;
    }

    //Returns if a player belongs to this controller
    public boolean playerIsInController(Player player)
    {
        return playerInController.containsValue(player);
    }

    public Set<CoJoinRole> getRolesFromPlayer(Player player)
    {
        Set<CoJoinRole> bufRoles = new HashSet<>();

        this.playerInController.forEach((role, player1) ->  {
            if(player1 != null && player1.getUniqueId().equals(player.getUniqueId()))
            {
                bufRoles.add(role);
            }
        });

        return bufRoles;
    }

    //Check if a player has a specific role in the Controller
    public boolean playerHasRole(Player player, CoJoinRole role)
    {

        return (playerInController.get(role) != null) && playerInController.get(role).equals(player);
    }
}
