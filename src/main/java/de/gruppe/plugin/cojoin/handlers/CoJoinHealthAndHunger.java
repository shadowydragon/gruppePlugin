package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class CoJoinHealthAndHunger implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if (event.getEntity() instanceof Player player)
        {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);
            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK))
            {
                updateHunger(controller, player);
            }
            else
            {
                event.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player player)
        {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK) || controller.playerHasRole(player, CoJoinRole.MOVEMENT_LOOKDIRECTION))
            {
                updateHealth(controller, player, 0);
            }


        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player player)
        {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);


            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK) || controller.playerHasRole(player, CoJoinRole.MOVEMENT_LOOKDIRECTION))
            {
                updateHealth(controller, player, event.getDamage());
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player)
        {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);


            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK) || controller.playerHasRole(player, CoJoinRole.MOVEMENT_LOOKDIRECTION))
            {
                if (event.getDamager() instanceof Player && controller.playerIsInController((Player) event.getDamager()))
                {
                    event.setCancelled(true);
                }
                updateHealth(controller, player, event.getDamage());
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
        {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);


        assert controller != null;
        if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK))
        {
            controller.getPlayersForController(player).forEach(player1 -> player1.getInventory().clear());
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getEntity() instanceof Player player)
        {
            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);


            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK))
            {
                controller.getPlayersForController(player).forEach(player1 -> player1.getActivePotionEffects().forEach(potionEffect -> player1.removePotionEffect(potionEffect.getType())));
                controller.getPlayersForController(player).forEach(player1 -> player1.addPotionEffects(player.getActivePotionEffects()));
            }
        }
    }

    private void updateHunger(CoJoinController controller, Player whoTrigger)
    {
        for (Player player : controller.getPlayersForController()) {
            if (player == null || player.equals(whoTrigger))
            {
                continue;
            }
            player.setFoodLevel(whoTrigger.getFoodLevel());
        }
    }

    private void updateHealth(CoJoinController controller, Player whoTrigger, double damage)
    {
        for (Player player : controller.getPlayersForController()) {
            if (player == null || player.equals(whoTrigger))
            {
                continue;
            }
            player.setHealth(whoTrigger.getHealth() - damage);
        }
    }

}
