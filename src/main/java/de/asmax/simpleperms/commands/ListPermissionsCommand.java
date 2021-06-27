package de.***REMOVED***.simpleperms.commands;

import de.***REMOVED***.simpleperms.Main;
import de.***REMOVED***.simpleperms.groups.GroupManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListPermissionsCommand implements CommandExecutor {

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

        if(!player.hasPermission("simpleperms.groups.permissions.list")) {
            player.sendMessage(error + "You don't have the Permission to do that.");
            return true;
        }

        if(args.length != 1) {
            player.sendMessage(error + "Please use: §9/spgplist <group>");
            return true;
        }

        String name = args[0];

        if(!GroupManager.getGroup(name)) {
            player.sendMessage(error + "The group §4" + name + " §fdoesn't exists.");
            return true;
        }

        String groupPermissions = GroupManager.listPermissions(name);
        player.sendMessage(prefix + "§aPermissions from §9" + name + "§a: §6" + groupPermissions);
        return true;
    }
}
