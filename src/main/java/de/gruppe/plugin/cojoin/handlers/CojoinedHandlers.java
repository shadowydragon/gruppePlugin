package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import org.bukkit.event.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CojoinedHandlers implements Listener
{


    @EventHandler
    public void handle(PlayerMoveEvent event) {
        if(Main.isConjoined == true) {
            try {
                Player playerMoving = event.getPlayer();





                if(playerMoving.getUniqueId().equals(Main.movementController))
                {
                    Player attackController = Bukkit.getPlayer(Main.attackController);
                    Player breakController = Bukkit.getPlayer(Main.breakController);
                    Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
                    if(!attackController.isDead() && !breakController.isDead() && !inventoryController.isDead()) {
                        if(!attackController.equals(playerMoving))
                        {
                            attackController.teleport(playerMoving.getLocation());
                        }
                        if(!breakController.equals(playerMoving))
                        {
                            breakController.teleport(playerMoving.getLocation());
                        }
                        if(!inventoryController.equals(playerMoving))
                        {
                            inventoryController.teleport(playerMoving.getLocation());
                        }
                    }
                }
                else {
                    event.setCancelled(true);


                }
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    @EventHandler
    public void handle(PlayerEggThrowEvent event) {
        if(Main.isConjoined == true) {
            Player thrower = event.getPlayer();

            if (thrower.equals(Main.attackController))
            {
                InventorySyncHandlers.attackControllerInventoryChange();
            }

        }
    }

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        if(Main.isConjoined == true) {
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getUniqueId().equals(Main.movementController))
            {
                InventorySyncHandlers.movementControllerInventoryChange();
            }
            else if (!event.getPlayer().getUniqueId().equals(Main.breakController) && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            {
                event.setCancelled(true);
            }

        }
    }




    @EventHandler
    public void handle(EntityToggleSwimEvent event) {
        if(Main.isConjoined == true && event.getEntityType() == EntityType.PLAYER && event.getEntity().getUniqueId().equals(Main.movementController)) {
            Bukkit.getPlayer(Main.attackController).setSwimming(true);
            Bukkit.getPlayer(Main.breakController).setSwimming(true);
            Bukkit.getPlayer(Main.inventoryController).setSwimming(true);
        }
    }

    @EventHandler
    public void handle(EntityDamageByEntityEvent event) {
        if(Main.isConjoined == true && event.getDamager().getType() == EntityType.PLAYER && !event.getDamager().getUniqueId().equals(Main.attackController)) {
            event.setCancelled(true);
        }
    }





}
