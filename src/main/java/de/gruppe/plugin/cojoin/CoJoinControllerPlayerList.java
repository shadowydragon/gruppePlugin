package de.gruppe.plugin.cojoin;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CoJoinControllerPlayerList {
    //List for the player who is controled from more player

    private static final List<CoJoinController> CO_JOIN_CONTROLLER_CONTROLLERS = new ArrayList<>();


    public static void addPlayerController(CoJoinController player)
    {
        CO_JOIN_CONTROLLER_CONTROLLERS.add(player);
    }

    public List<CoJoinController> getCoJoinPlayerControllers() {
        return CO_JOIN_CONTROLLER_CONTROLLERS;
    }

    public static List<String> getControllerNames()
    {
        List<String> buf = new LinkedList<>();
        for (CoJoinController coJoinControllerController : CO_JOIN_CONTROLLER_CONTROLLERS) {
            buf.add(coJoinControllerController.getControlerName());
        }

        return buf;
    }

    public static CoJoinController getControllerFromPlayer(Player player)
    {
        for (CoJoinController coJoinControllerController : CO_JOIN_CONTROLLER_CONTROLLERS) {
            if (coJoinControllerController.getPlayersForController().contains(player))
            {
                return coJoinControllerController;
            }
        }
        return null;
    }

    public static CoJoinController getControllerFromName(String name)
    {
        for (CoJoinController coJoinControllerController : CO_JOIN_CONTROLLER_CONTROLLERS) {
            if (coJoinControllerController.getControlerName().equalsIgnoreCase(name))
            {
                return coJoinControllerController;
            }
        }
        return null;
    }




}
