package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class CojoinedHandlers implements Listener {

    @EventHandler
    public void handle(PlayerMoveEvent event) {
        if (Main.isConjoined) {
            try {
                Player playerMoving = event.getPlayer();

                if (playerMoving.getUniqueId().equals(Main.movementController)) {
                    Player attackController = Bukkit.getPlayer(Main.attackController);
                    Player breakController = Bukkit.getPlayer(Main.breakController);
                    Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
                    if (attackController != null && breakController != null && inventoryController != null) {
                        if (!attackController.isDead() && !breakController.isDead() && !inventoryController.isDead()) {
                            if (!attackController.equals(playerMoving)) {
                                attackController.teleport(playerMoving.getLocation());
                            }
                            if (!breakController.equals(playerMoving)) {
                                breakController.teleport(playerMoving.getLocation());
                            }
                            if (!inventoryController.equals(playerMoving)) {
                                inventoryController.teleport(playerMoving.getLocation());
                            }
                        }
                    }
                } else {
                    event.setCancelled(true);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @EventHandler
    public void handle(PlayerEggThrowEvent event) {
        if (Main.isConjoined) {
            Player thrower = event.getPlayer();

            if (thrower.getUniqueId().equals(Main.attackController)) {
                InventorySyncHandlers.attackControllerInventoryChange();
            }

        }
    }

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        if (Main.isConjoined) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getUniqueId().equals(Main.movementController)) {
                InventorySyncHandlers.movementControllerInventoryChange();
            } else if (!event.getPlayer().getUniqueId().equals(Main.breakController) && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void handle(EntityToggleSwimEvent event) {
        if (Main.isConjoined && event.getEntityType() == EntityType.PLAYER && event.getEntity().getUniqueId().equals(Main.movementController)) {
            Objects.requireNonNull(Bukkit.getPlayer(Main.attackController)).setSwimming(true);
            Objects.requireNonNull(Bukkit.getPlayer(Main.breakController)).setSwimming(true);
            Objects.requireNonNull(Bukkit.getPlayer(Main.inventoryController)).setSwimming(true);
        }
    }

    @EventHandler
    public void handle(EntityDamageByEntityEvent event) {
        if (Main.isConjoined && event.getDamager().getType() == EntityType.PLAYER && !event.getDamager().getUniqueId().equals(Main.attackController)) {
            event.setCancelled(true);
        }
    }

}
