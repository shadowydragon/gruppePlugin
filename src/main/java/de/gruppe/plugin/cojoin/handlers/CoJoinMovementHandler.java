package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import de.gruppe.plugin.cojoin.CoJoinUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;
import java.util.Set;

public class CoJoinMovementHandler implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        //if player doesn't contain to any controller then nothing happen
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }

        //Check if player has the role for the look direction
        Set<Player> playersInController = Objects.requireNonNull(CoJoinControllerPlayerList.getControllerFromPlayer(player)).getPlayersForController();
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller != null && controller.getPlayerInController().containsValue(player)) {
            //System.out.println("moved!");

            if (((controller.getPlayerInController().get(CoJoinRole.MOVEMENT_LOOKDIRECTION)) != null &&
                    (controller.getPlayerInController().get(CoJoinRole.MOVEMENT_LOOKDIRECTION).equals(player))) ||
                    ((controller.getPlayerInController().get(CoJoinRole.MOVEMENT_WALK)) != null &&
                            (controller.getPlayerInController().get(CoJoinRole.MOVEMENT_WALK).equals(player)))) {

                CoJoinUtil.updatePlayerLocations(playersInController, player.getLocation(), player);
                return;
            }

            //TODO: separate movement
            /*if ((controller.getPlayerInController().get(CoJoinRole.MOVEMENT_LOOKDIRECTION)) != null &&
                    (controller.getPlayerInController().get(CoJoinRole.MOVEMENT_WALK))!= null &&
                    (controller.getPlayerInController().get(CoJoinRole.MOVEMENT_LOOKDIRECTION).equals(player)) &&
                    (controller.getPlayerInController().get(CoJoinRole.MOVEMENT_WALK).equals(player))){

                CoJoinUtil.updatePlayerLocations(playersInController, event.getTo(), player);
                return;
            }
            else if ((controller.getPlayerInController().get(CoJoinRole.MOVEMENT_LOOKDIRECTION) != null) &&
            (controller.getPlayerInController().get(CoJoinRole.MOVEMENT_LOOKDIRECTION).equals(player)))
            {
                Location lookDirection = event.getFrom();
                lookDirection.setYaw(event.getTo().getYaw());
                lookDirection.setPitch(event.getTo().getPitch());

                CoJoinUtil.updatePlayerLocations(playersInController, lookDirection, player);
                return;

            } else if ((controller.getPlayerInController().get(CoJoinRole.MOVEMENT_WALK) != null) &&
                    (controller.getPlayerInController().get(CoJoinRole.MOVEMENT_WALK).equals(player))) {
                System.out.println("hier");
                Location lookDirection = event.getTo();
                lookDirection.setYaw(event.getFrom().getYaw());
                lookDirection.setPitch(event.getFrom().getPitch());

                CoJoinUtil.updatePlayerLocations(playersInController, lookDirection, player);
                return;
            }*/

            event.setCancelled(true);
        }
        event.setCancelled(true);
    }

}
