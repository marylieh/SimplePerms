package de.***REMOVED***.simpleperms.commands;

import de.***REMOVED***.simpleperms.Main;
import de.***REMOVED***.simpleperms.groups.GroupManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddGroupCommand implements CommandExecutor {

    String prefix = Main.getInstance().getPrefix();
    String error = Main.getInstance().getError();
    private GroupManager groupManager = Main.getInstance().getGroupManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(error + "Just a Player can execute this command.");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("simpleperms.groups.add")) {
            player.sendMessage(error + "You don't have the Permission to do that.");
            return true;
        }

        if(args.length != 2) {
            player.sendMessage(error + "Please use: §9/spgadd <name> <level>");
            return true;
        }

        String name = args[0];

        try {

            int level = Integer.parseInt(args[1]);

            if(groupManager.getGroup(name)) {
               player.sendMessage(error + "The group §4" + name + " §fexists already.");
               return true;
            }

            groupManager.addGroup(name, level);
            player.sendMessage(prefix + "§aYou have successfully created the group: §6" + name);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            player.sendMessage(error + "Please use a NUMBER as 3th argument!");
        }

        return true;
    }
}
