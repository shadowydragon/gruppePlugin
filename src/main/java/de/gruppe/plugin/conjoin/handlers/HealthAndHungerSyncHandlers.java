package de.gruppe.plugin.conjoin.handlers;

import de.gruppe.plugin.Main;
import org.bukkit.event.Listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class HealthAndHungerSyncHandlers implements Listener {

    @EventHandler
    public void handle(EntityDamageEvent event) {
        if(Main.isConjoined == true) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK))
            {
                System.out.println("sweep");

                event.setCancelled(true);
                System.out.println(Main.attackController);
                System.out.println(Main.movementController);
                return;
            }else if(event.getEntityType() == EntityType.PLAYER) {
                UUID damagedPlayer = event.getEntity().getUniqueId();
                System.out.println("1");

                Player attackController = Bukkit.getPlayer(Main.attackController);
                Player breakController = Bukkit.getPlayer(Main.breakController);
                Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
                Player movementController = Bukkit.getPlayer(Main.movementController);


                if(damagedPlayer.equals(Main.movementController)) {
                    System.out.println("2");
                    if(movementController.getHealth() - event.getDamage() > 0)
                    {
                        System.out.println("3");
                        attackController.setHealth(movementController.getHealth() - event.getDamage());
                        breakController.setHealth(movementController.getHealth() - event.getDamage());
                        inventoryController.setHealth(movementController.getHealth() - event.getDamage());
                    }

                    else
                    {
                        attackController.setHealth(0);
                        breakController.setHealth(0);
                        inventoryController.setHealth(0);
                    }
                }
                else if(damagedPlayer.equals(Main.attackController)) {
                    System.out.println("2");
                    if(movementController.getHealth() - event.getDamage() > 0)
                    {
                        System.out.println("3");
                        movementController.setHealth(attackController.getHealth() - event.getDamage());
                        breakController.setHealth(attackController.getHealth() - event.getDamage());
                        inventoryController.setHealth(attackController.getHealth() - event.getDamage());
                    }

                    else
                    {
                        movementController.setHealth(0);
                        breakController.setHealth(0);
                        inventoryController.setHealth(0);
                    }
                }
                else if(damagedPlayer.equals(Main.breakController)) {
                    System.out.println("2");
                    if(movementController.getHealth() - event.getDamage() > 0)
                    {
                        System.out.println("3");
                        movementController.setHealth(breakController.getHealth() - event.getDamage());
                        attackController.setHealth(breakController.getHealth() - event.getDamage());
                        inventoryController.setHealth(breakController.getHealth() - event.getDamage());
                    }

                    else
                    {
                        movementController.setHealth(0);
                        attackController.setHealth(0);
                        inventoryController.setHealth(0);
                    }
                }
                else if(damagedPlayer.equals(Main.inventoryController)) {
                    System.out.println("2");
                    if(movementController.getHealth() - event.getDamage() > 0)
                    {
                        System.out.println("3");
                        movementController.setHealth(inventoryController.getHealth() - event.getDamage());
                        attackController.setHealth(inventoryController.getHealth() - event.getDamage());
                        breakController.setHealth(inventoryController.getHealth() - event.getDamage());
                    }

                    else
                    {
                        movementController.setHealth(0);
                        attackController.setHealth(0);
                        breakController.setHealth(0);
                    }
                }
            }
        }
    }


    @EventHandler
    public void handle(EntityRegainHealthEvent event) {
        if(Main.isConjoined == true) {
            if(event.getEntityType() == EntityType.PLAYER) {
                UUID damagedPlayer = event.getEntity().getUniqueId();

                Player attackController = Bukkit.getPlayer(Main.attackController);
                Player breakController = Bukkit.getPlayer(Main.breakController);
                Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
                Player movementController = Bukkit.getPlayer(Main.movementController);


                if(damagedPlayer.equals(Main.attackController))
                {
                    if(attackController.getHealth() + event.getAmount() < 20)
                    {
                        breakController.setHealth(attackController.getHealth() + event.getAmount());
                        inventoryController.setHealth(attackController.getHealth() + event.getAmount());
                        movementController.setHealth(attackController.getHealth() + event.getAmount());
                    }
                    else
                    {
                        breakController.setHealth(20);
                        inventoryController.setHealth(20);
                        movementController.setHealth(20);
                    }
                }
                else if(damagedPlayer.equals(Main.breakController))
                {
                    if(attackController.getHealth() + event.getAmount() < 20)
                    {
                        attackController.setHealth(breakController.getHealth() + event.getAmount());
                        inventoryController.setHealth(breakController.getHealth() + event.getAmount());
                        movementController.setHealth(breakController.getHealth() + event.getAmount());
                    }
                    else
                    {
                        attackController.setHealth(20);
                        inventoryController.setHealth(20);
                        movementController.setHealth(20);
                    }
                }
                else if(damagedPlayer.equals(Main.inventoryController))
                {
                    if(attackController.getHealth() + event.getAmount() < 20)
                    {
                        attackController.setHealth(inventoryController.getHealth() + event.getAmount());
                        breakController.setHealth(inventoryController.getHealth() + event.getAmount());
                        movementController.setHealth(inventoryController.getHealth() + event.getAmount());
                    }
                    else
                    {
                        attackController.setHealth(20);
                        breakController.setHealth(20);
                        movementController.setHealth(20);
                    }
                }
                else if(damagedPlayer.equals(Main.movementController))
                {
                    if(attackController.getHealth() + event.getAmount() < 20)
                    {
                        attackController.setHealth(movementController.getHealth() + event.getAmount());
                        breakController.setHealth(movementController.getHealth() + event.getAmount());
                        inventoryController.setHealth(movementController.getHealth() + event.getAmount());
                    }
                    else
                    {
                        attackController.setHealth(20);
                        breakController.setHealth(20);
                        inventoryController.setHealth(20);
                    }
                }

            }
        }
    }

    @EventHandler
    public void handle(FoodLevelChangeEvent event) {
        if(Main.isConjoined == true) {
            if(event.getEntityType() == EntityType.PLAYER) {
                UUID playerWhoAte = event.getEntity().getUniqueId();
                if(playerWhoAte.equals(Main.breakController) && event.getFoodLevel() > ((Player)event.getEntity()).getFoodLevel())
                {
                    Player attackController = Bukkit.getPlayer(Main.attackController);
                    Player breakController = Bukkit.getPlayer(Main.breakController);
                    Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
                    Player movementController = Bukkit.getPlayer(Main.movementController);


                    attackController.setFoodLevel(event.getFoodLevel());
                    attackController.setSaturation(breakController.getSaturation());
                    attackController.setExhaustion(breakController.getExhaustion());

                    inventoryController.setFoodLevel(event.getFoodLevel());
                    inventoryController.setSaturation(breakController.getSaturation());
                    inventoryController.setExhaustion(breakController.getExhaustion());

                    movementController.setFoodLevel(event.getFoodLevel());
                    movementController.setSaturation(breakController.getSaturation());
                    movementController.setExhaustion(breakController.getExhaustion());
                }
            }
        }
    }


    public static void syncHunger() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player attackController = Bukkit.getPlayer(Main.attackController);
                Player breakController = Bukkit.getPlayer(Main.breakController);
                Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
                Player movementController = Bukkit.getPlayer(Main.movementController);


                attackController.setFoodLevel(movementController.getFoodLevel());
                attackController.setSaturation(movementController.getSaturation());
                attackController.setExhaustion(movementController.getExhaustion());

                breakController.setFoodLevel(movementController.getFoodLevel());
                breakController.setSaturation(movementController.getSaturation());
                breakController.setExhaustion(movementController.getExhaustion());

                inventoryController.setFoodLevel(movementController.getFoodLevel());
                inventoryController.setSaturation(movementController.getSaturation());
                inventoryController.setExhaustion(movementController.getExhaustion());
            }
        }.runTaskTimer(Main.plugin, 900l, 300l);
    }

}
