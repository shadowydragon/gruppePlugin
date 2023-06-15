package de.gruppe.plugin.cojoin.commands;

import de.gruppe.plugin.cojoin.CoJoinPlayer;
import de.gruppe.plugin.cojoin.CoJoinPlayerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import de.gruppe.plugin.cojoin.CoJoinUtil;
import org.bukkit.Bukkit;
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
            }
        }

        Player senderPlayer = (Player) sender;



        //The commands wehre the sender don't have set a target player
        if (args.length == 1)
        {
            if (CoJoinUtil.checkStringIsRole(args[0]))
            {

            }
        }


        if (args.length == 2)
        {

            //If the command is the createController
            if (args[0].equalsIgnoreCase("createController"))
            {
                //Set in the Controllerlist a new generatet controller with the name in args[1]
                CoJoinPlayerPlayerList.addPlayerController(new CoJoinPlayer(args[1]));
            }

            if (args.length == 1)
            {
                //The player get a message with all availabals roles
                if (args[0].equalsIgnoreCase("getRoles"))
                {
                    for (CoJoinRole value : CoJoinRole.values()) {
                        senderPlayer.sendMessage(value.name());
                    }
                }
            }

            if (args.length == 2)
            {
                //The player get all empty roles from the controller
                if (args[0].equalsIgnoreCase("getRoles"))
                {
                    for (String controllerName : CoJoinPlayerPlayerList.getControllerNames()) {
                        if (args[1].equalsIgnoreCase(controllerName))
                        {
                            CoJoinPlayer controller = CoJoinPlayerPlayerList.getControllerFromName(controllerName);

                            controller.addCoJoinPlayerRole(senderPlayer, CoJoinRole.valueOf(args[1].toUpperCase()));
                        }
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
                        }
                    }
                }
            }


        }

        return true;
    }
}
