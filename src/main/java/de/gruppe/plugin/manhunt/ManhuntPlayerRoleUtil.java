package de.gruppe.plugin.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

public class ManhuntPlayerRoleUtil {

    public static void playerAddRole(UUID player, ManhuntRoles role) {
        //Bukkit.getPlayer(player).getPersistentDataContainer().get(new NamespacedKey(player.toString(), "Manhunt_Role"), PersistentDataType.STRING);
        //Setzen von einem Tag das angibt welche rolle ein spieler bei der manhunt hat
        //namespaced key gibt an worunter man darauf zugreifen kann
        //PersistentDataType, gibt an was fuer ein wert gespeichert wird
        //Und der dritte wert ist der gespeicherte wert
        Objects.requireNonNull(Bukkit.getPlayer(player)).getPersistentDataContainer().set(new NamespacedKey(player.toString(), ManhuntRoles.MANHUNTROLLE.name().toLowerCase()), PersistentDataType.STRING, role.toString());
    }

    public static String getManhuntRole(Player player) {
        return player.getPersistentDataContainer().get(new NamespacedKey(player.getUniqueId().toString(), ManhuntRoles.MANHUNTROLLE.name().toLowerCase()), PersistentDataType.STRING);
    }

    public static void changeRole(Player player, ManhuntRoles role) {
        player.getPersistentDataContainer().set(new NamespacedKey(player.getUniqueId().toString(), ManhuntRoles.MANHUNTROLLE.name().toLowerCase()), PersistentDataType.STRING, role.name().toLowerCase());
    }
}
