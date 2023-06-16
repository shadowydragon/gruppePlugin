package de.gruppe.plugin.manhunt.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.manhunt.ManhuntPlayerRoleUtil;
import de.gruppe.plugin.manhunt.ManhuntRoles;
import de.gruppe.plugin.manhunt.ManhuntUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.persistence.PersistentDataType;

public class ManhuntPlayerHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player joinedPlayer = event.getPlayer();

        if (joinedPlayer.getPersistentDataContainer().get(new NamespacedKey(joinedPlayer.getUniqueId().toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING) == null)
        {
            ManhuntPlayerRoleUtil.playerAddRole(joinedPlayer.getUniqueId(), ManhuntRoles.NONE);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDamageByPlayer(EntityDamageByEntityEvent event) {

        if (Main.plugin.getConfig().getBoolean("ManhuntFriendlyFire"))
        {
            if (event.getDamager() instanceof Player)
            {
                Player damageDealer = (Player) event.getDamager();
                if (event.getEntity() instanceof Player)
                {

                    Player damageReciver = (Player) event.getEntity();

                    //Check if both player from role none if not they cant do damage
                    if (ManhuntPlayerRoleUtil.getManhuntRole(damageDealer).equalsIgnoreCase(ManhuntRoles.NONE.name()))
                    {
                        if (!(ManhuntPlayerRoleUtil.getManhuntRole(damageReciver).equalsIgnoreCase(ManhuntRoles.NONE.name())))
                        {
                            event.setCancelled(true);
                            return;
                        }
                    }
                    else if (ManhuntPlayerRoleUtil.getManhuntRole(damageDealer).equalsIgnoreCase(ManhuntRoles.SPEEDRUNNER.name()))
                    {
                        if (ManhuntPlayerRoleUtil.getManhuntRole(damageReciver).equalsIgnoreCase(ManhuntRoles.NONE.name()))
                        {
                            event.setCancelled(true);
                            return;
                        }
                        if (ManhuntPlayerRoleUtil.getManhuntRole(damageReciver).equalsIgnoreCase(ManhuntRoles.SPEEDRUNNER.name()))
                        {
                            event.setCancelled(true);
                            return;
                        }

                    }
                    else if (ManhuntPlayerRoleUtil.getManhuntRole(damageDealer).equalsIgnoreCase(ManhuntRoles.HUNTER.name()))
                    {
                        if (ManhuntPlayerRoleUtil.getManhuntRole(damageReciver).equalsIgnoreCase(ManhuntRoles.NONE.name()))
                        {
                            event.setCancelled(true);
                            return;
                        }
                        if (ManhuntPlayerRoleUtil.getManhuntRole(damageReciver).equalsIgnoreCase(ManhuntRoles.HUNTER.name()))
                        {
                            event.setCancelled(true);
                            return;
                        }

                    }
                }
            }
        }

    }
}
