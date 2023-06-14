package de.gruppe.plugin.manhunt.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.manhunt.ManhuntPlayerRoleUtil;
import de.gruppe.plugin.manhunt.menu.ManhuntFollowSelectMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.HashMap;
import java.util.UUID;

public class CompassHandler implements Listener
{

    private static HashMap<UUID, UUID> userTarget = new HashMap<>(); //first is the key second the target
    @EventHandler
    public void rightClickCompass(PlayerInteractEvent event)
    {
        event.getAction();


            if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Targeter"))
            {
                //TODO: Implement to get a window to chose who will targeted
                ManhuntFollowSelectMenu manhuntFollowSelectMenu = new ManhuntFollowSelectMenu(Main.getPlayerMenuUtility((Player) event.getPlayer()));
                manhuntFollowSelectMenu.open();
            }



        try {
            if ((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Targeter"))
            {
                event.getPlayer().sendMessage("Dein target befindet sich in der: " + Bukkit.getPlayer(userTarget.get(event.getPlayer().getUniqueId())).getLocation().getWorld().getName());
            }
        }
        catch (Exception e)
        {

        }

    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event)
    {
        UUID playerUid = event.getPlayer().getUniqueId();

/*        for (UUID uuid : userTarget.keySet()) {
            if (userTarget.get(uuid).equals(playerUid))
            {
                Player userPlayer = Bukkit.getPlayer(uuid);
                Player targetPlayer = Bukkit.getPlayer(userTarget.get(uuid));

                //System.out.println(userPlayer.getWorld().getName());

                if (userPlayer.getWorld().getName().equals("world"))
                {
                    System.out.println("target: " + targetPlayer.getLocation());
                    System.out.println("Compass: " + userPlayer.getCompassTarget());
                    System.out.println("hello");
                    userPlayer.setCompassTarget(targetPlayer.getLocation());
                }
                if (targetPlayer.getWorld().getName().equals("world_nether"))
                {
                    //System.out.println("true");
                    //userPlayer.setCompassTarget(targetPlayer.getLocation());
                    if (userPlayer.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Targeter"))
                    {
                        System.out.println("target: " + targetPlayer.getLocation());
                        System.out.println("Compass: " + userPlayer.getCompassTarget());
                        ItemStack compass = userPlayer.getInventory().getItemInMainHand();
                        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                        compassMeta.setLodestone(targetPlayer.getLocation());
                        compassMeta.setLodestoneTracked(true);
                        compass.setItemMeta(compassMeta);
                        userPlayer.getInventory().setItem(userPlayer.getInventory().getHeldItemSlot(), compass);
                    }
                }

            }
        }*/

        if (userTarget.containsKey(playerUid))
        {
            Player userPlayer = Bukkit.getPlayer(playerUid);
            Player targetPlayer = Bukkit.getPlayer(userTarget.get(playerUid));

            if (userPlayer.getWorld().getName().equals("world"))
            {
                userPlayer.setCompassTarget(targetPlayer.getLocation());
            }
            if (targetPlayer.getWorld().getName().equals("world_nether"))
            {
                //System.out.println("true");
                //userPlayer.setCompassTarget(targetPlayer.getLocation());
                if (userPlayer.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Targeter"))
                {
                    ItemStack compass = userPlayer.getInventory().getItemInMainHand();
                    CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                    compassMeta.setLodestone(targetPlayer.getLocation());
                    compassMeta.setLodestoneTracked(true);
                    compass.setItemMeta(compassMeta);
                    userPlayer.getInventory().setItem(userPlayer.getInventory().getHeldItemSlot(), compass);
                }
            }
        }
    }

    public static void setUserTarget(UUID user, UUID target)
    {
        userTarget.replace(user, target);
    }

    public static boolean containsUsertTarget(UUID user)
    {
        return userTarget.containsKey(user);
    }

    public static void addUserTarget(UUID user, UUID target)
    {
        userTarget.put(user, target);
    }
}
