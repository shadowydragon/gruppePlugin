package de.gruppe.plugin.manhunt.handlers;

import de.gruppe.plugin.manhunt.ManhuntPlayerRoleUtil;
import de.gruppe.plugin.manhunt.ManhuntRoles;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
}
