package de.gruppe.plugin.cojoin.commands;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import de.gruppe.plugin.cojoin.config.CoJoinSaveConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CoJoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //check if the user of the command is a player
        //if not then the command won't do anything
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage("You can't get this Role because you aren't a player");
                return true;
            }
        }

        assert sender instanceof Player;
        Player senderPlayer = (Player) sender;

        //if you use the command without any arguments you get a list for possible arguments
        if (args.length == 0) {
            String s = "/Cojoin ";

            senderPlayer.sendMessage(ChatColor.RED + "To less Arguments try: ");
            senderPlayer.sendMessage(s + ChatColor.YELLOW + "createController " + ChatColor.AQUA + "<charactercontrollername>");
            senderPlayer.sendMessage(s + ChatColor.YELLOW + "getController ");
            senderPlayer.sendMessage(s + ChatColor.YELLOW + "getRoles " + ChatColor.AQUA + "<charactercontrollername>");
            senderPlayer.sendMessage(s + ChatColor.YELLOW + "setRole " + ChatColor.AQUA + "<charactercontrollername> " + ChatColor.GREEN + "<roleName>");
        }

        if (args.length == 1) {
            //The player get a message with all available roles
            if (args[0].equalsIgnoreCase("getRoles")) {
                //CoJoinRole.values get a set with all available roles
                for (CoJoinRole value : CoJoinRole.values()) {
                    if (value == CoJoinRole.COJOINROLE) {
                        continue;
                    }
                    senderPlayer.sendMessage(ChatColor.BLUE + value.name());
                    System.out.println(value.name());
                }
                return true;
            }

            //Get all created controllers
            if (args[0].equalsIgnoreCase("getController")) {
                senderPlayer.sendMessage("Controller Names:");
                for (String controllerName : CoJoinControllerPlayerList.getControllerNames()) {
                    senderPlayer.sendMessage(ChatColor.GOLD + controllerName);
                }
            }

            if (args[0].equalsIgnoreCase("save")) {
                int controllerNumber = 0;
                for (CoJoinController controller : CoJoinControllerPlayerList.getCoJoinPlayerControllers()) {
                    CoJoinSaveConfig.set("Controllers.Controller_Names." + ++controllerNumber, controller.getControllerName());

                    File file = new File("./plugins/Saves/", "cojoinSaves.yml");
                    if (file.exists()) {
                        file.delete();
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    for (CoJoinRole value : CoJoinRole.values()) {

                        if (value == CoJoinRole.COJOINROLE) {
                            continue;
                        }

                        if (controller.getPlayerInController().get(value) == null) {
                            CoJoinSaveConfig.set("Controllers.Controller_PlayerRoles." + controllerNumber + "." + value.name() + "_" + controllerNumber, "null");

                            continue;
                        }

                        CoJoinSaveConfig.set("Controllers.Controller_PlayerRoles." + controllerNumber + "." + value.name() + "_" + controllerNumber, controller.getPlayerInController().get(value).getName());
                    }
                }
                System.out.println("Saved");
            }

            if (args[0].equalsIgnoreCase("load")) {
                int number = 0;

                while (CoJoinSaveConfig.contains("Controllers.Controller_Names." + ++number)) {
                    String controllerName = (String) CoJoinSaveConfig.get("Controllers.Controller_Names." + number);

                    if (CoJoinControllerPlayerList.getControllerFromName(controllerName) == null) {
                        CoJoinControllerPlayerList.addPlayerController(new CoJoinController(controllerName));
                    }

                    for (CoJoinRole value : CoJoinRole.values()) {

                        if (value == CoJoinRole.COJOINROLE) {
                            continue;
                        }

                        if (CoJoinSaveConfig.contains("Controllers.Controller_PlayerRoles." + number + "." + value.name() + "_" + number)) {
                            Object obj = CoJoinSaveConfig.get("Controllers.Controller_PlayerRoles." + number + "." + value.name() + "_" + number);

                            if (obj instanceof String stingValue) {

                                if (stingValue.equalsIgnoreCase("null")) {
                                    Objects.requireNonNull(CoJoinControllerPlayerList.getControllerFromName(controllerName)).addCoJoinPlayerRole(null, value);
                                } else {
                                    Player player = Bukkit.getPlayer(stingValue);
                                    Objects.requireNonNull(CoJoinControllerPlayerList.getControllerFromName(controllerName)).addCoJoinPlayerRole(player, value);
                                }
                            }
                        }

                    }

                }
            }
        }

        if (args.length == 2) {

            //If the command is the createController
            if (args[0].equalsIgnoreCase("createController")) {
                //Set in the Controller list a new generated controller with the name in args[1]
                CoJoinControllerPlayerList.addPlayerController(new CoJoinController(args[1]));
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Der Character " + ChatColor.DARK_BLUE + args[1] + ChatColor.GREEN + " wurde erstellt.");
                return true;
            }

            if (args.length == 2) {
                //The player get all empty roles from the controller
                if (args[0].equalsIgnoreCase("getRoles")) {
                    //check if the second argument is from an existing controller
                    for (String controllerName : CoJoinControllerPlayerList.getControllerNames()) {
                        if (args[1].equalsIgnoreCase(controllerName)) {
                            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromName(controllerName);

                            senderPlayer.sendMessage(ChatColor.YELLOW + "Diese Rollen sind nicht vergeben:");
                            assert controller != null;
                            for (CoJoinRole emptyRole : controller.getEmptyRoles()) {
                                senderPlayer.sendMessage(ChatColor.RED + emptyRole.name());
                                System.out.println(emptyRole.name());
                            }

                            senderPlayer.sendMessage(ChatColor.YELLOW + "Diese Rollen sind vergeben:");
                            if (controller.getSetRoleAndPlayer() != null) {
                                for (String s : controller.getSetRoleAndPlayer()) {
                                    senderPlayer.sendMessage(ChatColor.GREEN + s);
                                }
                            }

                        }
                    }
                    return true;
                }

            }
        }

        //argument[0]: command name
        //argument[1]: controller name
        //argument[2]: role name
        //argument[3]: optional player who get the role if not used the sender got the role
        //add a player to a role from a controller
        if (args.length == 3) {

            if (args[0].equalsIgnoreCase("setRole")) {
                for (String controllerName : CoJoinControllerPlayerList.getControllerNames()) {
                    if (args[1].equalsIgnoreCase(controllerName)) {
                        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromName(controllerName);

                        assert controller != null;
                        controller.addCoJoinPlayerRole(senderPlayer, CoJoinRole.valueOf(args[2].toUpperCase()));
                        if (!controller.playerHasRole(senderPlayer, CoJoinRole.MOVEMENT_WALK) && CoJoinRole.valueOf(args[2].toUpperCase()) != CoJoinRole.MOVEMENT_WALK) {
                            senderPlayer.setCollidable(false);
                            senderPlayer.setInvulnerable(true);
                        } else {
                            senderPlayer.setCollidable(true);
                            senderPlayer.setInvulnerable(false);
                        }

                        System.out.println(args[2].toUpperCase());

                    }
                }
                return true;
            }
        }

        return true;
    }
}
