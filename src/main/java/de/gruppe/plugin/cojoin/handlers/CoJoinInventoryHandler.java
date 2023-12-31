package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Set;

public class CoJoinInventoryHandler implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

        if (event.getPlayer() instanceof Player player) {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            updateInventories(controller);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player player) {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            assert controller != null;
            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
                updateInventories(controller);
                event.setCancelled(true);
            } else {
                updateInventories(controller);
                return;
            }
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {

        Player player = event.getPlayer();

        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }

        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller != null && !controller.playerHasRole(player, CoJoinRole.BREAK)) {
            event.setCancelled(true);
        } else {
            Player invPlayer = Objects.requireNonNull(controller).getPlayerForRole(CoJoinRole.INVENTORY);
            invPlayer.getInventory().setItemInMainHand(event.getItemStack());
            updateInventories(controller);
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }

        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        if (controller != null && !controller.playerHasRole(player, CoJoinRole.PLACE)) {
            event.setCancelled(true);
        } else {
            Player invPlayer = Objects.requireNonNull(controller).getPlayerForRole(CoJoinRole.INVENTORY);
            invPlayer.getInventory().setItemInMainHand(event.getItemStack());
            updateInventories(controller);
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (!controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
            updateInventories(controller);
            event.setCancelled(true);
        } else {
            updateInventories(controller);
        }
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            assert controller != null;
            if (!controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
                player.setCanPickupItems(false);
                updateInventories(controller);
                event.setCancelled(true);
                return;
            } else {
                updateInventories(controller);
                return;
            }
        }

    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        //Synchronized the inventory when the Player for the inventory change the held item
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (!controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
            event.setCancelled(true);
            return;
        } else {

            controller.getPlayersForController().forEach((player1) -> player1.getInventory().setHeldItemSlot(event.getNewSlot()));

            updateInventories(controller);
        }
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        //Synchronized the inventory if item get damage
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (!controller.playerHasRole(player, CoJoinRole.INVENTORY)) {

            Player invPlayer = controller.getPlayerForRole(CoJoinRole.INVENTORY);

            if (invPlayer.getInventory().getItemInMainHand().equals(event.getItem())) {
                invPlayer.getInventory().getItemInMainHand().setDurability((short) (event.getItem().getDurability() + event.getDamage()));
            } else if (invPlayer.getInventory().getItemInOffHand().equals(event.getItem())) {
                invPlayer.getInventory().getItemInOffHand().setDurability((short) (event.getItem().getDurability() + event.getDamage()));
            } else {
                for (ItemStack armorContent : invPlayer.getInventory().getArmorContents()) {
                    if (armorContent.equals(event.getItem())) {
                        armorContent.setDurability((short) (event.getItem().getDurability() + event.getDamage()));
                    }
                }
            }

        }
        updateInventories(controller);
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {

        if (event.getEntity() instanceof Player player) {

            //If player doesn't belong to a controller he can do it
            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }
            //Synchronized the inventory if
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            assert controller != null;
            if (controller.playerHasRole(player, CoJoinRole.ATTACK)) {
                Player invPlayer = controller.getPlayerForRole(CoJoinRole.INVENTORY);
                if (invPlayer.equals(player)) {
                    return;
                }
                ItemStack shotItem = invPlayer.getInventory().getItem(invPlayer.getInventory().first(Objects.requireNonNull(event.getConsumable())));
                assert shotItem != null;
                if (shotItem.getAmount() > 0) {
                    shotItem.setAmount(shotItem.getAmount() - 1);
                }

            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {

        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        //Synchronized the inventory if  item get damage
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
            controller.getPlayersForController(player).forEach((player1 -> player1.setExp(player.getExp())));
            controller.getPlayersForController(player).forEach(player1 -> player1.giveExp(event.getAmount()));
        }
    }

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
            controller.getPlayersForController(player).forEach(player1 -> player1.giveExp(event.getExpToDrop()));
        }
    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
            controller.getPlayersForController(player).forEach((player1 -> player1.setLevel(event.getNewLevel())));
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        //If player doesn't belong to a controller he can do it
        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
            return;
        }
        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

        assert controller != null;
        if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK)) {
            controller.getPlayerForRole(CoJoinRole.INVENTORY).getInventory().getItemInMainHand().setAmount(controller.getPlayerForRole(CoJoinRole.INVENTORY).getInventory().getItemInMainHand().getAmount() - 1);
            updateInventories(controller);
        }
    }

    private void updateInventories(CoJoinController controller) {
        Set<Player> players = controller.getPlayersForController();

        for (Player player : players) {
            if (controller.playerHasRole(player, CoJoinRole.INVENTORY)) {
                continue;
            }
            player.getInventory().setContents(controller.getPlayerInController().get(CoJoinRole.INVENTORY).getInventory().getContents());
        }
    }

    private void updateInventories(CoJoinController controller, Player playerTrigger) {
        Set<Player> players = controller.getPlayersForController();

        for (Player player : players) {
            if (player.equals(playerTrigger)) {
                continue;
            }
            player.getInventory().setContents(playerTrigger.getInventory().getContents());
        }
    }
}
