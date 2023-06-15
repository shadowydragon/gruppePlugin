package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
}
