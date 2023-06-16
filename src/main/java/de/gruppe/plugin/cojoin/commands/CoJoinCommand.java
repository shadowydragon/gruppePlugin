package de.gruppe.plugin.cojoin.commands;

import de.gruppe.plugin.cojoin.CoJoinPlayer;
import de.gruppe.plugin.cojoin.CoJoinPlayerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import de.gruppe.plugin.cojoin.CoJoinUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoJoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
        {
            if (args.length == 0)
            {
                sender.sendMessage("You can't get this Role because you aren't a player");
                return true;
            }
        }


        Player senderPlayer = (Player) sender;

        if (args.length == 1)
        {
            //The player get a message with all availabals roles
            if (args[0].equalsIgnoreCase("getRoles"))
            {
                //CoJoinRole.values get a set with all availables roles
                for (CoJoinRole value : CoJoinRole.values()) {
                    if (value == CoJoinRole.COJOINROLE)
                    {
                        continue;
                    }
                    senderPlayer.sendMessage(ChatColor.BLUE + value.name());
                    System.out.println(value.name());
                }
                return true;
            }

            //Get all created controllers
            if (args[0].equalsIgnoreCase("getController"))
            {
                senderPlayer.sendMessage("Controller Names:");
                for (String controllerName : CoJoinPlayerPlayerList.getControllerNames()) {
                    senderPlayer.sendMessage(ChatColor.GOLD + controllerName);
                }
            }
        }


        if (args.length == 2)
        {

            //If the command is the createController
            if (args[0].equalsIgnoreCase("createController"))
            {
                //Set in the Controllerlist a new generatet controller with the name in args[1]
                CoJoinPlayerPlayerList.addPlayerController(new CoJoinPlayer(args[1]));
                senderPlayer.sendMessage(ChatColor.GREEN + "Der Character " + ChatColor.DARK_BLUE +  args[1] + ChatColor.GREEN + " wurde erstellt");
                return true;
            }


            if (args.length == 2)
            {
                //The player get all empty roles from the controller
                if (args[0].equalsIgnoreCase("getRoles"))
                {
                    //check if the second argument is from an existing controller
                    for (String controllerName : CoJoinPlayerPlayerList.getControllerNames()) {
                        if (args[1].equalsIgnoreCase(controllerName))
                        {
                            CoJoinPlayer controller = CoJoinPlayerPlayerList.getControllerFromName(controllerName);

                            senderPlayer.sendMessage(ChatColor.YELLOW + "Diese Rollen sind nicht vergeben:");
                            for (CoJoinRole emptyRole : controller.getEmptyRoles()) {
                                senderPlayer.sendMessage(ChatColor.RED + emptyRole.name());
                                System.out.println(emptyRole.name());
                            }

                            senderPlayer.sendMessage(ChatColor.YELLOW + "Diese Rollen sind vergeben:");
                            if (controller.getSetRoleAndPlayer() != null)
                            {
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
        //argument[3]: opptional player who get the role if not used the sender got the role
        if (args.length == 3)
        {

            if (args[0].equalsIgnoreCase("setRole"))
            {
                for (String controllerName : CoJoinPlayerPlayerList.getControllerNames()) {
                    if (args[1].equalsIgnoreCase(controllerName))
                    {
                        CoJoinPlayer controller = CoJoinPlayerPlayerList.getControllerFromName(controllerName);

                        controller.addCoJoinPlayerRole(senderPlayer, CoJoinRole.valueOf(args[2].toUpperCase()));
                        System.out.println(args[2].toUpperCase());

                    }
                }
                return true;
            }
        }

        return true;
    }
}
