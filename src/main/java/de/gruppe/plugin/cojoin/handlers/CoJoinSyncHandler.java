package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerExpChangeEvent;

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
                updateHunger(controller, player);
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
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
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
            controller.getPlayersForController(player).forEach(player1 -> player1.getInventory().clear());
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
                controller.getPlayersForController(walkPlayer).forEach(player1 -> player1.addPotionEffects(walkPlayer.getActivePotionEffects()));
            }
        }
    }

    @EventHandler
    public void levelChange(PlayerExpChangeEvent event) {
        Player attackController = Bukkit.getPlayer(Main.attackController);
        Player breakController = Bukkit.getPlayer(Main.breakController);
        Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
        Player movementController = Bukkit.getPlayer(Main.movementController);

        Player eventPlayer = event.getPlayer();
        if (attackController != null && breakController != null && inventoryController != null && movementController != null) {
            if (eventPlayer.equals(attackController)) {
                breakController.giveExp(event.getAmount());
                inventoryController.giveExp(event.getAmount());
                movementController.giveExp(event.getAmount());
            } else if (eventPlayer.equals(breakController)) {
                attackController.giveExp(event.getAmount());
                inventoryController.giveExp(event.getAmount());
                movementController.giveExp(event.getAmount());
            } else if (eventPlayer.equals(inventoryController)) {
                attackController.giveExp(event.getAmount());
                breakController.giveExp(event.getAmount());
                movementController.giveExp(event.getAmount());
            } else if (eventPlayer.equals(movementController)) {
                attackController.giveExp(event.getAmount());
                breakController.giveExp(event.getAmount());
                inventoryController.giveExp(event.getAmount());
            }
        }
    }

    private void updateHunger(CoJoinController controller, Player whoTrigger) {
        for (Player player : controller.getPlayersForController()) {
            if (player == null || player.equals(whoTrigger)) {
                continue;
            }
            player.setFoodLevel(whoTrigger.getFoodLevel());
        }
    }

    private void updateHealth(CoJoinController controller, Player trigger, double damage) {
        for (Player player : controller.getPlayersForController()) {
            if (player == null || player.equals(trigger)) {
                continue;
            }
            player.setHealth(trigger.getHealth() - damage);
        }
    }

}
