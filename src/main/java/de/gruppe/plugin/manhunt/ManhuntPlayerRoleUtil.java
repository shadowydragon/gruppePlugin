package de.gruppe.plugin.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class ManhuntPlayerRoleUtil {

    public static void playerAddRole(UUID player, ManhuntRoles role)
    {
        //Bukkit.getPlayer(player).getPersistentDataContainer().get(new NamespacedKey(player.toString(), "Manhunt_Role"), PersistentDataType.STRING);
        //Setzen von einem tag das angibt welche rolle ein spieler bei der manhunt hat
        //namespaced key gibt an worunter man darauf zugreifen kann
        //PersistentDataType gibt an was fuer ein wert gespeichert wird
        //Und der dritte wert ist der gespeicherte wert
        Bukkit.getPlayer(player).getPersistentDataContainer().set(new NamespacedKey(player.toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING, role.toString());
    }

    public static String getManhuntRole(Player player)
    {
        return player.getPersistentDataContainer().get(new NamespacedKey(player.getUniqueId().toString(), ManhuntRoles.MANHUNTROLE.name().toLowerCase()), PersistentDataType.STRING);
    }
}
