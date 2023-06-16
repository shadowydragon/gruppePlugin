package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CoJoinActionsHandler implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
        {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (!controller.playerHasRole(player, CoJoinRole.PLACE))
        {
            event.setCancelled(true);
        }
    }

}
