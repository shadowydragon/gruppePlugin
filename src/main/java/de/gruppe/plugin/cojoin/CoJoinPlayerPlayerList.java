package de.gruppe.plugin.cojoin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CoJoinPlayerPlayerList{
    //List for the player who is controled from more player

    private static final List<CoJoinPlayer> coJoinPlayerControllers = new ArrayList<>();


    public static void addPlayerController(CoJoinPlayer player)
    {
        coJoinPlayerControllers.add(player);
    }

    public List<CoJoinPlayer> getCoJoinPlayerControllers() {
        return coJoinPlayerControllers;
    }

    public static List<String> getControllerNames()
    {
        List<String> buf = new LinkedList<>();
        for (CoJoinPlayer coJoinPlayerController : coJoinPlayerControllers) {
            buf.add(coJoinPlayerController.getControlerName());
        }

        return buf;
    }

    public static CoJoinPlayer getControllerFromName(String name)
    {
        for (CoJoinPlayer coJoinPlayerController : coJoinPlayerControllers) {
            if (coJoinPlayerController.getControlerName().equalsIgnoreCase(name))
            {
                return coJoinPlayerController;
            }
        }
        return null;
    }
}
