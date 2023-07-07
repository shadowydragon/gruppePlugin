package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class CoJoinSyncHandler implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);
            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK)) {
                updateHunger(controller, player, event.getFoodLevel());
            } else {
                event.setCancelled(true);
            }

        }
    }



    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player player) {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK) || controller.playerHasRole(player, CoJoinRole.MOVEMENT_LOOKDIRECTION)) {
                updateHealth(controller, player, 0);
            }

        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK) || controller.playerHasRole(player, CoJoinRole.MOVEMENT_LOOKDIRECTION)) {
                updateHealth(controller, player, event.getFinalDamage());
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {

        if (event.getHitEntity() != null && event.getHitEntity() instanceof Player hitPlayer) {
            //System.out.println("hit");
            //hitPlayer.sendMessage("you are hit");
            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(hitPlayer) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(hitPlayer);

            if (controller != null && !controller.playerHasRole(hitPlayer, CoJoinRole.MOVEMENT_WALK)) {
                //System.out.println("cancel");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            assert controller != null;
            if (event.getDamager() instanceof Player && controller.playerIsInController((Player) event.getDamager())) {
                event.setCancelled(true);
            }
            updateHealth(controller, player, event.getFinalDamage());
        }
    }

    @EventHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {

            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }

            if (event.getEntity() instanceof Mob target) {
                CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

                assert controller != null;
                Player walkPlayer = controller.getPlayerForRole(CoJoinRole.MOVEMENT_WALK);

                if (walkPlayer.equals(player)) {
                    return;
                }
                target.setTarget(walkPlayer);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK)) {

            controller.getPlayersForController(player).stream().forEach(player1 ->
            {
                player1.getInventory().clear();
                player1.teleport(player.getWorld().getSpawnLocation());
                player1.setHealth(player1.getMaxHealth());
                player1.setFoodLevel(20);
                player1.setSaturation(20);
                player1.setLevel(0);
                player1.setExp(0);
            });
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        System.out.println("Potion hit");
        if (event.getEntity() instanceof Player walkPlayer) {
            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(walkPlayer) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(walkPlayer);

            assert controller != null;
            if (controller.playerHasRole(walkPlayer, CoJoinRole.MOVEMENT_WALK)) {
                System.out.printf("Potion remove");
                controller.getPlayersForController(walkPlayer).forEach(player1 -> player1.getActivePotionEffects().forEach(potionEffect -> player1.removePotionEffect(potionEffect.getType())));
                System.out.println("Potion add");
                //controller.getPlayersForController(walkPlayer).forEach(player1 -> player1.addPotionEffects(walkPlayer.getActivePotionEffects()));
                controller.getPlayersForController(walkPlayer)
                        .forEach(player1 -> walkPlayer.getActivePotionEffects()
                        .forEach(potionEffect -> player1.addPotionEffect(potionEffect, true)));
                if (event.getNewEffect() != null){
                    controller.getPlayersForController(walkPlayer).forEach(player -> player.addPotionEffect(event.getNewEffect()));
                }
                controller.getPlayersForController(walkPlayer).forEach((player -> System.out.println(player.getName())));
            }
        }
    }

    private void updateHunger(CoJoinController controller, Player whoTrigger, int foodLevel) {
        for (Player player : controller.getPlayersForController()) {
            if (player == null || player.equals(whoTrigger)) {
                continue;
            }
            player.setFoodLevel(foodLevel);
            player.setSaturation(whoTrigger.getSaturation());
        }
    }

    private void updateHealth(CoJoinController controller, Player trigger, double damage) {
        for (Player player : controller.getPlayersForController()) {
            if (player == null || player.equals(trigger)) {
                continue;
            }

            if ((trigger.getHealth() - damage) < 0)
            {
                return;
            }

            player.setHealth(trigger.getHealth() - damage);
        }
    }

}
