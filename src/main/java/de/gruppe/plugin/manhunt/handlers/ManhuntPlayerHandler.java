package de.gruppe.plugin.manhunt.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.manhunt.ManhuntPlayerRoleUtil;
import de.gruppe.plugin.manhunt.ManhuntRoles;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class ManhuntPlayerHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joinedPlayer = event.getPlayer();

        if (joinedPlayer.getPersistentDataContainer().get(new NamespacedKey(joinedPlayer.getUniqueId().toString(), ManhuntRoles.MANHUNTROLLE.name().toLowerCase()), PersistentDataType.STRING) == null) {
            ManhuntPlayerRoleUtil.playerAddRole(joinedPlayer.getUniqueId(), ManhuntRoles.NONE);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDamageByPlayer(EntityDamageByEntityEvent event) {

        if (Main.plugin.getConfig().getBoolean("ManhuntFriendlyFire")) {
            if (event.getDamager() instanceof Player damageDealer) {
                if (event.getEntity() instanceof Player damageReceiver) {

                    //Check if both player from role none if not they cant do damage
                    if (ManhuntPlayerRoleUtil.getManhuntRole(damageDealer).equalsIgnoreCase(ManhuntRoles.NONE.name())) {
                        if (!(ManhuntPlayerRoleUtil.getManhuntRole(damageReceiver).equalsIgnoreCase(ManhuntRoles.NONE.name()))) {
                            event.setCancelled(true);
                            return;
                        }
                    } else if (ManhuntPlayerRoleUtil.getManhuntRole(damageDealer).equalsIgnoreCase(ManhuntRoles.SPEEDRUNNER.name())) {
                        if (ManhuntPlayerRoleUtil.getManhuntRole(damageReceiver).equalsIgnoreCase(ManhuntRoles.NONE.name())) {
                            event.setCancelled(true);
                            return;
                        }
                        if (ManhuntPlayerRoleUtil.getManhuntRole(damageReceiver).equalsIgnoreCase(ManhuntRoles.SPEEDRUNNER.name())) {
                            event.setCancelled(true);
                            return;
                        }

                    } else if (ManhuntPlayerRoleUtil.getManhuntRole(damageDealer).equalsIgnoreCase(ManhuntRoles.HUNTER.name())) {
                        if (ManhuntPlayerRoleUtil.getManhuntRole(damageReceiver).equalsIgnoreCase(ManhuntRoles.NONE.name())) {
                            event.setCancelled(true);
                            return;
                        }
                        if (ManhuntPlayerRoleUtil.getManhuntRole(damageReceiver).equalsIgnoreCase(ManhuntRoles.HUNTER.name())) {
                            event.setCancelled(true);
                            return;
                        }

                    }
                }
            }
        }

    }
}
