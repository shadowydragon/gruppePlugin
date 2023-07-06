package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerHandlers implements Listener
{
    @EventHandler
    public void levelChange(PlayerExpChangeEvent event)
    {
        Player attackController = Bukkit.getPlayer(Main.attackController);
        Player breakController = Bukkit.getPlayer(Main.breakController);
        Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
        Player movementController = Bukkit.getPlayer(Main.movementController);

        Player eventPlayer = event.getPlayer();
        if (eventPlayer.equals(attackController))
        {
            breakController.giveExp(event.getAmount());
            inventoryController.giveExp(event.getAmount());
            movementController.giveExp(event.getAmount());
        }else if (eventPlayer.equals(breakController))
        {
            attackController.giveExp(event.getAmount());
            inventoryController.giveExp(event.getAmount());
            movementController.giveExp(event.getAmount());
        }else if (eventPlayer.equals(inventoryController))
        {
            attackController.giveExp(event.getAmount());
            breakController.giveExp(event.getAmount());
            movementController.giveExp(event.getAmount());
        }else if (eventPlayer.equals(movementController))
        {
            attackController.giveExp(event.getAmount());
            breakController.giveExp(event.getAmount());
            inventoryController.giveExp(event.getAmount());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Enemy)
        {
            Enemy target = (Enemy) event.getEntity();

            if (event.getDamager() instanceof Player player)
            {
                CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

                assert controller != null;
                Player walkPlayer = controller.getPlayerForRole(CoJoinRole.MOVEMENT_WALK);

                if (walkPlayer.equals(player))
                {
                    return;
                }



                target.setLastDamageCause(new EntityDamageByEntityEvent(walkPlayer, target, event.getCause(), event.getDamage()));
            }


        }
    }
}
