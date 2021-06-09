package de.***REMOVED***.simpleperms.commands;

import de.***REMOVED***.simpleperms.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPermissionCommand implements CommandExecutor {

    private String prefix = Main.getInstance().getPrefix();
    private String error = Main.getInstance().getError();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(error + "Just a Player can execute this command.");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("simpleperms.get")) {
            player.sendMessage(error + "You don't have the Permission to do that.");
            return true;
        }

        if(args.length != 2) {
            player.sendMessage(error + "Please use: §9/spget <user> <permission>");
            return true;
        }

        String userName = args[0];
        Player user = Bukkit.getPlayer(userName);
        String perm = args[1];

        if(user == null) {
            player.sendMessage(error + "The player is not Online.");
            return true;
        }

        boolean havePermission = Main.getInstance().getPermissionManager().getPermission(user, perm);

        if(havePermission) {
            player.sendMessage(prefix + "§aThe player: §6" + userName + " §a§lhas §r§athe Permission: §6" + perm);
        } else {
            player.sendMessage(prefix + "§aThe player: §6" + userName + " §a§lhasn't §r§athe Permission: §6" + perm);
        }

        return true;
    }
}
