package de.***REMOVED***.simpleperms.commands;

import de.***REMOVED***.simpleperms.Main;
import de.***REMOVED***.simpleperms.groups.GroupManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ListPlayerInGroupCommand implements CommandExecutor {

    String prefix = Main.getInstance().getPrefix();
    String error = Main.getInstance().getError();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(error + "Just a Player can execute this command.");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("simpleperms.groups.player.list")) {
            player.sendMessage(error + "You don't have the Permission to do that.");
            return true;
        }

        if(args.length == 0) {
            player.sendMessage(error + "Please use: §9/spgpllist <group>");
            return true;
        } else if(args.length == 2 && args[1].equalsIgnoreCase("offlinemode")) {

            String group = args[0];

            if(!GroupManager.getGroup(group)) {
                player.sendMessage(error + "The Group §4" + group + "§f doesn't exists.");
                return true;
            }
            ArrayList<String> playerList = (ArrayList<String>) GroupManager.listPlayers(group);
            player.sendMessage(prefix + "§aIn the group: §b" + group + " §aare the following Players: §6" + playerList);
            return true;
        }

        String group = args[0];

        if(!GroupManager.getGroup(group)) {
            player.sendMessage(error + "The Group §4" + group + "§f doesn't exists.");
            return true;
        }

        ArrayList<String> playerList = (ArrayList<String>) GroupManager.listPlayers(group);
        ArrayList<String> playerListDecompiled = new ArrayList<String>();

        int offline = 0;

        for(int i = 0; i < playerList.size(); i++) {
            String playerUUID = playerList.get(i);

            if(!(playerUUID.equalsIgnoreCase("DoNotTouchThis"))) {

                UUID uuid = UUID.fromString(playerUUID);

                Player user = Bukkit.getPlayer(uuid);

                if(user == null) {
                    offline++;
                    break;
                }

                String name = user.getName();
                playerListDecompiled.add(name);

            }
        }

        player.sendMessage(prefix + "§aIn the group: §b" + group + " §aare the following Players: §6" + playerListDecompiled);

        if(offline != 0) {
            player.sendMessage("§7[NOTE] §8§o" + offline + " players are offline, try: §r§7/spgpllist <group> offlinemode, to get the raw uuid's.");
        }
        return true;
    }
}
