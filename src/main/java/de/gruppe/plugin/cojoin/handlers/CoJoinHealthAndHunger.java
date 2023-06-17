package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class CoJoinHealthAndHunger implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {

        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);
            updateHunger(controller, player);
        }

    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);
            updateHealth(controller, player);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);
            updateHealth(controller, player);
        }

    }

    private void updateHunger(CoJoinController controller, Player whoTrigger)
    {
        for (Player player : controller.getPlayersForController()) {
            if (player.equals(whoTrigger))
            {
                continue;
            }
            player.setFoodLevel(whoTrigger.getFoodLevel());
        }
    }

    private void updateHealth(CoJoinController controller, Player whoTrigger)
    {
        for (Player player : controller.getPlayersForController()) {
            if (player.equals(whoTrigger))
            {
                continue;
            }
            player.setHealth(whoTrigger.getHealth());
        }
    }

}
