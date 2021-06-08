package de.***REMOVED***.simpleperms.commands;

import de.***REMOVED***.simpleperms.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddTempPermission implements CommandExecutor {

    String prefix = Main.getInstance().getPrefix();
    String error = Main.getInstance().getError();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(error + "Just a Player can execute this command.");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("simpleperms.add.temp")) {
            player.sendMessage(error + "You don't have the Permission to do that.");
            return true;
        }

        if(args.length != 3) {
            player.sendMessage(error + "Please use: §9/sptemp <user> <permission> <time>");
            return true;
        }

        String userName = args[0];
        Player user = Bukkit.getPlayer(userName);
        String perm = args[1];
        long time;

        if(user == null) {
            player.sendMessage(error + "The player is not Online.");
            return true;
        }

        if(user.hasPermission(perm)) {
            player.sendMessage(error + "The Player has already the permission: §4" + perm);
            return true;
        }

        try {

            time = Long.parseLong(args[2]);

            Main.getInstance().getPermissionManager().setTempPermission(user, perm, time);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            player.sendMessage(error + "Please use a NUMBER for the TIME");
        }

        return true;
    }
}
