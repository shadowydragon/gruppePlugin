package de.gruppe.plugin.manhunt.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.manhunt.ManhuntUtil;
import de.gruppe.plugin.manhunt.menu.ManhuntFollowSelectMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class CompassHandler implements Listener {

    private static HashMap<UUID, UUID> userTarget = new HashMap<>(); //first is the key second the target

    public static void setUserTarget(UUID user, UUID target) {
        userTarget.replace(user, target);
    }

    public static boolean containsUserTarget(UUID user) {
        return userTarget.containsKey(user);
    }

    public static void addUserTarget(UUID user, UUID target) {
        userTarget.put(user, target);
    }

    @EventHandler
    public void rightClickCompass(PlayerInteractEvent event) {
        event.getAction();

        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR && ManhuntUtil.checkCompassDisplayname(event.getPlayer().getInventory().getItemInMainHand())) {
            ManhuntFollowSelectMenu manhuntFollowSelectMenu = new ManhuntFollowSelectMenu(Main.getPlayerMenuUtility(event.getPlayer()));
            manhuntFollowSelectMenu.open(event.getPlayer());
        }

        try {
            if ((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) && ManhuntUtil.checkCompassDisplayname(event.getPlayer().getInventory().getItemInMainHand())) {
                event.getPlayer().sendMessage(Objects.requireNonNull(Bukkit.getPlayer(userTarget.get(event.getPlayer().getUniqueId()))).getName() + " befindet sich in der: " + Objects.requireNonNull(Objects.requireNonNull(Bukkit.getPlayer(userTarget.get(event.getPlayer().getUniqueId()))).getLocation().getWorld()).getName());
                Location targetLocation = Objects.requireNonNull(Bukkit.getPlayer(userTarget.get(event.getPlayer().getUniqueId()))).getLocation();

                if (event.getPlayer().getLocation().getWorld() == targetLocation.getWorld()) {
                    CompassMeta meta = (CompassMeta) Objects.requireNonNull(event.getItem()).getItemMeta();
                    Objects.requireNonNull(meta).setLodestone(targetLocation);
                    event.getItem().setItemMeta(meta);
                }
            }
        } catch (Exception e) {

        }

    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        UUID playerUid = event.getPlayer().getUniqueId();

        if (userTarget.containsKey(playerUid)) {
            Player userPlayer = Bukkit.getPlayer(playerUid);
            Player targetPlayer = Bukkit.getPlayer(userTarget.get(playerUid));

            if (Objects.requireNonNull(userPlayer).getLocation().getWorld() == Objects.requireNonNull(targetPlayer).getLocation().getWorld()) {
                CompassMeta meta = (CompassMeta) Objects.requireNonNull(userPlayer.getInventory().getItem(userPlayer.getInventory().first(Material.COMPASS))).getItemMeta();
                //meta.setLodestoneTracked(false);
                Objects.requireNonNull(meta).setLodestone(targetPlayer.getLocation());
                Objects.requireNonNull(userPlayer.getInventory().getItem(userPlayer.getInventory().first(Material.COMPASS))).setItemMeta(meta);
            }

        }
    }
}
