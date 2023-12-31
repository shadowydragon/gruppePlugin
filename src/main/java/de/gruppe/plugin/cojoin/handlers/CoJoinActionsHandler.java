package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class CoJoinActionsHandler implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller != null) {
            if (!controller.playerHasRole(player, CoJoinRole.PLACE)) {
                event.setCancelled(true);
            }

            controller.getPlayerInController().get(CoJoinRole.INVENTORY).getInventory().getItemInMainHand().setAmount(
                    controller.getPlayerInController().get(CoJoinRole.INVENTORY).getInventory().getItemInMainHand().getAmount() - 1);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);
            if (controller != null) {
                if (!controller.playerHasRole(player, CoJoinRole.ATTACK)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);
        if (controller != null) {
            if (!controller.playerHasRole(player, CoJoinRole.BREAK)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller != null) {
            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
                event.setCancelled(true);
            }
        }
    }
}
