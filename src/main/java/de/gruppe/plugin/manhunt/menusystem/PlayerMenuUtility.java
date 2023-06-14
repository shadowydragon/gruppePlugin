package de.gruppe.plugin.manhunt.menusystem;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {

    private Player owner;

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner.getPlayer();
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
