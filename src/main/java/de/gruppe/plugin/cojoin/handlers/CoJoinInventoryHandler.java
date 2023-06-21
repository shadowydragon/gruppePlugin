package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import de.gruppe.plugin.cojoin.CoJoinUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Set;

public class CoJoinInventoryHandler implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {


            if (event.getPlayer() instanceof Player)
            {
                Player player = (Player) event.getPlayer();

                //If player doesn't belong to a controller he can do it
                if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
                {
                    return;
                }
                CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

                if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
                {
                    event.setCancelled(true);
                }
                else
                {
                    updateInventorys(controller);
                    return;
                }
            }

            event.setCancelled(true);


    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player)
        {
            Player player = (Player) event.getWhoClicked();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
            {
                event.setCancelled(true);
            }
            else
            {
                updateInventorys(controller);
                return;
            }
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {

        if (event.getPlayer() instanceof Player)
        {
            Player player = (Player) event.getPlayer();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
            {
                event.setCancelled(true);
            }
            else
            {
                updateInventorys(controller);
            }
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
            {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
            {
                player.setCanPickupItems(false);
                event.setCancelled(true);
                return;
            }
            else
            {
                updateInventorys(controller);
                return;
            }
        }


    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
        {
            return;
        }
        //Syncronized the inventory when the Player for the inentory change the held item
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
        {
            event.setCancelled(true);
            return;
        }
        else
        {

            controller.getPlayersForController().forEach((player1) -> player1.getInventory().setHeldItemSlot(event.getNewSlot()));

            updateInventorys(controller);
        }
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {

        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
        {
            return;
        }
        //Syncronized the inventory if  item get damgage
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);





        if (!controller.playerHasRole(player, CoJoinRole.INVENTORY))
        {

            if (event.getItem().getItemMeta() instanceof ArmorMeta)
            {

                controller.getPlayerForRole(CoJoinRole.INVENTORY).getInventory().getItem(controller.getPlayerForRole(CoJoinRole.INVENTORY).getInventory().first(event.getItem())).setDurability(
                        (short) (controller.getPlayerForRole(CoJoinRole.INVENTORY).getInventory().getItem(controller.getPlayerForRole(CoJoinRole.INVENTORY).getInventory().first(event.getItem())).getDurability() -1)
                );

            }

            controller.getPlayerInController().get(CoJoinRole.INVENTORY).getInventory().getItemInMainHand().setDurability(
                    (short) (controller.getPlayerInController().get(CoJoinRole.INVENTORY).getInventory().getItemInMainHand().getDurability() - event.getDamage() - event.getDamage())

            );
        }
        updateInventorys(controller);
    }


    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {

        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
        {
            return;
        }
        //Syncronized the inventory if  item get damgage
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK))
        {
            controller.getPlayersForController(player).forEach((player1 -> player1.setExp(player.getExp())));
        }
    }

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
        {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller.playerHasRole(player, CoJoinRole.INVENTORY))
        {
            controller.getPlayersForController(player).forEach(player1 -> player1.giveExp(event.getExpToDrop()));

        }
    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
        {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK))
        {
            controller.getPlayersForController(player).forEach((player1 -> player1.setLevel(event.getNewLevel())));
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null)
        {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK))
        {
            controller.getPlayerForRole(CoJoinRole.INVENTORY).getInventory().getItemInMainHand().setAmount(
                    controller.getPlayerForRole(CoJoinRole.INVENTORY).getInventory().getItemInMainHand().getAmount() - 1);
            updateInventorys(controller);
        }
    }



    private void updateInventorys(CoJoinController controller)
    {
        Set<Player> players = controller.getPlayersForController();

        for (Player player : players) {
            if (controller.playerHasRole(player, CoJoinRole.INVENTORY))
            {
                continue;
            }
            player.getInventory().setContents(controller.getPlayerInController().get(CoJoinRole.INVENTORY).getInventory().getContents());
        }
    }

    private void updateInventorys(CoJoinController controller, Player playerTrigger)
    {
        Set<Player> players = controller.getPlayersForController();

        for (Player player : players) {
            if (player.equals(playerTrigger))
            {
                continue;
            }
            player.getInventory().setContents(playerTrigger.getInventory().getContents());
        }
    }
}
