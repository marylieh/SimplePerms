package de.***REMOVED***.simpleperms.commands;

import de.***REMOVED***.simpleperms.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePermissionCommand implements CommandExecutor {

    private String prefix = Main.getInstance().getPrefix();
    private String error = Main.getInstance().getError();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(error + "Just a Player can execute this command.");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("simpleperms.remove")) {
            player.sendMessage(error + "You don't have the Permission to do that.");
            return true;
        }

        if(args.length != 2) {
            player.sendMessage(error + "Please use: §9/spremove <user> <permission>");
            return true;
        }

        String userName = args[0];
        Player user = Bukkit.getPlayer(userName);
        String perm = args[1];

        if(user == null) {
            player.sendMessage(error+ "The player is not Online.");
            return true;
        }

        if(!user.hasPermission(perm)) {
            player.sendMessage(error + "The player hasn't the permission not yet");
            return true;
        }

        Main.getInstance().getPermissionManager().removePermission(user, perm);
        Main.getInstance().getPermissionManager().removeSavedPlayerPermissions(user, perm);
        player.sendMessage(prefix + "§aYou have successfully removed the permission: §6" + perm + " §afrom: §6" + userName);

        return true;
    }
}
