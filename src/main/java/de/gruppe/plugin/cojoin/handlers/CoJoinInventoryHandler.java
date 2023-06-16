package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import de.gruppe.plugin.cojoin.CoJoinUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class CoJoinInventoryHandler implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {


            if (event.getPlayer() instanceof Player)
            {
                Player player = (Player) event.getPlayer();

                if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
                {
                    return;
                }
                CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

                if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
                {
                    event.setCancelled(true);
                }
            }

            event.setCancelled(true);


    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

    }
}
