package de.***REMOVED***.simpleperms.commands;

import de.***REMOVED***.simpleperms.Main;
import de.***REMOVED***.simpleperms.groups.GroupManager;
import de.***REMOVED***.simpleperms.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;

public class UnsetPlayerGroupCommand implements CommandExecutor {

    String prefix = Main.getInstance().getPrefix();
    String error = Main.getInstance().getError();

    Config config = Main.getInstance().getConfiguration();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(error + "Just a Player can execute this command.");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("simpleperms.groups.player.remove")) {
            player.sendMessage(error + "You don't have the Permission to do that.");
            return true;
        }

        if(args.length != 2) {
            player.sendMessage(error + "Please use: §9/spgplrem <player> <group>");
            return true;
        }

        String userName = args[0];
        String group = args[1];

        Player user = Bukkit.getPlayer(userName);

        if(!GroupManager.getGroup(group)) {
            player.sendMessage(error + "The Group §4" + group + "§f doesn't exists.");
            return true;
        }

        if(user == null) {
            player.sendMessage(prefix + "The Player §4" + userName + " §fisn't online.");
            return true;
        }

        if(!GroupManager.getPlayerGroup(user, group)) {
            player.sendMessage(error + "The Player §4" + userName + "§f aren't in the Group: §4" + group);
            return true;
        }

        @SuppressWarnings("unchecked")
        List<String> tempPermissionList = (List<String>) config.getConfig().getList("Groups." + group + ".permissions");

        for(int n = 0; n < tempPermissionList.size(); n++) {
            String permission = tempPermissionList.get(n);
            System.out.println(permission);

            PermissionAttachment attachment = user.addAttachment(Main.getInstance());
            attachment.unsetPermission(permission);

        }

        GroupManager.removePlayerFromGroup(user, group);
        user.kickPlayer(prefix + "§2You're Group has been updated!");
        player.sendMessage(prefix + "§aYou have successfully §cremoved §6" + userName + " §afrom Group: §b" + group);
        return true;
    }
}
