package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import de.gruppe.plugin.cojoin.CoJoinUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.Set;

public class CoJoinInventoryHandler implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {


            if (event.getPlayer() instanceof Player)
            {
                Player player = (Player) event.getPlayer();

                //If player doesn't belong to a controller he can do it
                if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
                {
                    return;
                }
                CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

                if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
                {
                    event.setCancelled(true);
                }
                else
                {
                    updateInventorys(controller);
                }
            }

            event.setCancelled(true);


    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player)
        {
            Player player = (Player) event.getWhoClicked();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
            {
                event.setCancelled(true);
            }
            else
            {
                updateInventorys(controller);
            }
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {

        if (event.getPlayer() instanceof Player)
        {
            Player player = (Player) event.getPlayer();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
            {
                event.setCancelled(true);
            }
            else
            {
                updateInventorys(controller);
            }
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
            {
                event.setCancelled(true);
                player.setCanPickupItems(false);
                return;
            }
            else
            {
                updateInventorys(controller);
            }
        }

        event.setCancelled(true);
    }

    private void updateInventorys(CoJoinController controller)
    {
        Set<Player> players = controller.getPlayersForController();

        for (Player player : players) {
            if (controller.playerHasRole(player, CoJoinRole.INVENTORY))
            {
                continue;
            }
            player.getInventory().setContents(controller.getPlayerInController().get(CoJoinRole.INVENTORY).getInventory().getContents());
        }
    }
}
