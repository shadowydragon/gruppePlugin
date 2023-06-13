package de.gruppe.plugin.conjoin.handlers;

import de.gruppe.plugin.Main;
import org.bukkit.event.Listener;


import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class InventorySyncHandlers implements Listener {

    @EventHandler
    public void handle(InventoryClickEvent event) {
        if(Main.isConjoined == true) {
            HumanEntity entity = event.getWhoClicked();
            if(entity != null && entity instanceof Player) {
                UUID playerWhoClicked = entity.getUniqueId();
                if(event.getInventory().getType() == InventoryType.CRAFTING) {
                    if(playerWhoClicked.equals(Main.inventoryController))
                    {
                        inventoryControllerInventoryChange();
                    }else
                    {
                        event.setCancelled(true);
                    }
                }
                else {
                    if(!playerWhoClicked.equals(Main.inventoryController))
                        event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handle(CraftItemEvent event)
    {
        if(Main.isConjoined == true) {


            UUID player = event.getWhoClicked().getUniqueId();
            if(player.equals(Main.inventoryController))
            {
                inventoryControllerInventoryChange();
            }
            else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handle(InventoryEvent event)
    {
        if(Main.isConjoined == true) {


            UUID player = event.getViewers().get(0).getUniqueId();
            if(player.equals(Main.inventoryController))
            {
                inventoryControllerInventoryChange();
            }
            else {

            }
        }
    }



    @EventHandler
    public void handle(PlayerSwapHandItemsEvent event){
        if(Main.isConjoined == true) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(Main.inventoryController))
            {
                inventoryControllerInventoryChange();
            }
            else {
                event.setCancelled(true);
            }
        }
    }



    @EventHandler
    public void handle(InventoryDragEvent event) {
        if(Main.isConjoined == true) {
            HumanEntity entity = event.getWhoClicked();
            if(entity != null && entity instanceof Player) {
                UUID playerWhoClicked = entity.getUniqueId();
                if(event.getInventory().getType() == InventoryType.CRAFTING) {
                    if(playerWhoClicked.equals(Main.inventoryController))
                        inventoryControllerInventoryChange();
                    else
                    {
                        event.setCancelled(true);
                    }
                }
                else {
                    if(!playerWhoClicked.equals(Main.inventoryController))
                        event.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void handle(EntityPickupItemEvent event) {
        if(Main.isConjoined == true) {
            if(event.getEntity() != null && event.getEntityType() == EntityType.PLAYER) {
                UUID playerWhoClicked = event.getEntity().getUniqueId();
                if(playerWhoClicked.equals(Main.attackController)) {
                    attackControllerInventoryChange();
                }
                else if(playerWhoClicked.equals(Main.breakController))
                {
                    breakControllerInventoryChange();
                }
                else if(playerWhoClicked.equals(Main.inventoryController))
                {
                    inventoryControllerInventoryChange();
                }
                else if(playerWhoClicked.equals(Main.movementController))
                {
                    movementControllerInventoryChange();
                }
            }
        }
    }


    @EventHandler
    public void handle(PlayerDropItemEvent event) {
        if(Main.isConjoined == true) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(Main.inventoryController)) {
                inventoryControllerInventoryChange();
            }
            else
            {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void handle(PlayerItemConsumeEvent event) {
        if(Main.isConjoined == true) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(Main.breakController))
            {
                breakControllerInventoryChange();
            }
            else
            {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void handle(BlockPlaceEvent event) {
        if(Main.isConjoined == true) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(Main.movementController))
                movementControllerInventoryChange();
            else
            {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void handle(PlayerChangedMainHandEvent event)
    {
        if(Main.isConjoined) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(Main.inventoryController))
                inventoryControllerInventoryChange();
        }
    }

    @EventHandler
    public void handle(InventoryOpenEvent event)
    {

        if(Main.isConjoined == true) {


            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(Main.inventoryController))
                inventoryControllerInventoryChange();

            if (player.equals(Main.inventoryController)) {
                inventoryControllerInventoryChange();
            } else if (player.equals(Main.attackController)) {
                attackControllerInventoryChange();
            } else if (player.equals(Main.breakController)) {
                breakControllerInventoryChange();
            } else if (player.equals(Main.movementController)) {
                movementControllerInventoryChange();
            }

        }
    }


    @EventHandler
    public void handle(FurnaceExtractEvent event) throws InterruptedException {

        if(Main.isConjoined == true) {


            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(Main.inventoryController)) {
                Thread.sleep(5000);
                inventoryControllerInventoryChange();
                System.out.println("lalala");
            }

        }
    }



    @EventHandler
    public void handle(PlayerItemHeldEvent event)
    {
        if(Main.isConjoined) {
            UUID player = event.getPlayer().getUniqueId();

            Player attackController = Bukkit.getPlayer(Main.attackController);
            Player breakController = Bukkit.getPlayer(Main.breakController);
            Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
            Player movementController = Bukkit.getPlayer(Main.movementController);

            if(player.equals(Main.inventoryController))
            {
                attackController.getInventory().setHeldItemSlot(event.getNewSlot());
                breakController.getInventory().setHeldItemSlot(event.getNewSlot());
                movementController.getInventory().setHeldItemSlot(event.getNewSlot());

            }
            else
            {
                event.setCancelled(true);
            }
        }
    }


    public static void attackControllerInventoryChange() {
        Player attackController = Bukkit.getPlayer(Main.attackController);
        Player breakController = Bukkit.getPlayer(Main.breakController);
        Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
        Player movementController = Bukkit.getPlayer(Main.movementController);

        new BukkitRunnable() {
            int tick = 10;
            @Override
            public void run() {
                if(tick == 5) {
                    attackController.updateInventory();
                    tick--;
                }
                else if(tick == 0) {
                    breakController.getInventory().setContents(attackController.getInventory().getContents());
                    inventoryController.getInventory().setContents(attackController.getInventory().getContents());
                    movementController.getInventory().setContents(attackController.getInventory().getContents());

                    tick--;
                }
                else if(tick == -1) {
                    breakController.updateInventory();
                    inventoryController.updateInventory();
                    movementController.updateInventory();
                    this.cancel();
                }
                else
                    tick--;

            }
        }.runTaskTimer(Main.plugin, 0l, 1);
    }


    public static void breakControllerInventoryChange() {
        Player attackController = Bukkit.getPlayer(Main.attackController);
        Player breakController = Bukkit.getPlayer(Main.breakController);
        Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
        Player movementController = Bukkit.getPlayer(Main.movementController);

        new BukkitRunnable() {
            int tick = 10;
            @Override
            public void run() {
                if(tick == 5) {
                    breakController.updateInventory();
                    tick--;
                }
                else if(tick == 0) {
                    attackController.getInventory().setContents(breakController.getInventory().getContents());
                    inventoryController.getInventory().setContents(breakController.getInventory().getContents());
                    movementController.getInventory().setContents(breakController.getInventory().getContents());

                    tick--;
                }
                else if(tick == -1) {
                    attackController.updateInventory();
                    inventoryController.updateInventory();
                    movementController.updateInventory();
                    this.cancel();
                }
                else
                    tick--;

            }
        }.runTaskTimer(Main.plugin, 0l, 1);
    }


    public static void inventoryControllerInventoryChange() {
        Player attackController = Bukkit.getPlayer(Main.attackController);
        Player breakController = Bukkit.getPlayer(Main.breakController);
        Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
        Player movementController = Bukkit.getPlayer(Main.movementController);

        new BukkitRunnable() {
            int tick = 10;
            @Override
            public void run() {
                if(tick == 5) {
                    inventoryController.updateInventory();
                    tick--;
                }
                else if(tick == 0) {
                    attackController.getInventory().setContents(inventoryController.getInventory().getContents());
                    breakController.getInventory().setContents(inventoryController.getInventory().getContents());
                    movementController.getInventory().setContents(inventoryController.getInventory().getContents());

                    tick--;
                }
                else if(tick == -1) {
                    attackController.updateInventory();
                    breakController.updateInventory();
                    movementController.updateInventory();
                    this.cancel();
                }
                else
                    tick--;

            }
        }.runTaskTimer(Main.plugin, 0l, 1);
    }




    public static void movementControllerInventoryChange() {
        try
        {
            Player attackController = Bukkit.getPlayer(Main.attackController);
            Player breakController = Bukkit.getPlayer(Main.breakController);
            Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
            Player movementController = Bukkit.getPlayer(Main.movementController);

            new BukkitRunnable() {
                int tick = 10;
                @Override
                public void run() {
                    try {
                        if(tick == 5) {
                            movementController.updateInventory();
                            tick--;
                        }
                        else if(tick == 0) {
                            attackController.getInventory().setContents(movementController.getInventory().getContents());
                            breakController.getInventory().setContents(movementController.getInventory().getContents());
                            inventoryController.getInventory().setContents(movementController.getInventory().getContents());

                            tick--;
                        }
                        else if(tick == -1) {
                            attackController.updateInventory();
                            breakController.updateInventory();
                            inventoryController.updateInventory();
                            this.cancel();
                        }
                        else
                            tick--;
                    }catch (Exception e)
                    {

                    }

                }
            }.runTaskTimer(Main.plugin, 0l, 1);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }



}
