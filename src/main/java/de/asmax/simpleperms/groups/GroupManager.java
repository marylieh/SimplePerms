package de.***REMOVED***.simpleperms.groups;

import de.***REMOVED***.simpleperms.Main;
import de.***REMOVED***.simpleperms.utils.Config;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    //TODO: Add Players to groups and inherit their permissions
    private static Config config = Main.getInstance().getConfiguration();

    public static void addGroup(String group, int level) {
        ArrayList<String> tempList = new ArrayList<String>();
        ArrayList<String> playertempList = new ArrayList<String>();
        tempList.add("spacer.space");
        playertempList.add("DoNotTouchThis");

        config.getConfig().set("Groups." + group + ".level", level);
        config.getConfig().set("Groups." + group + ".permissions", tempList);
        config.getConfig().set("Groups." + group + ".players", playertempList);
        config.save();
    }

    public static void addPlayerToGroup(Player player, String group) {
        if(!getGroup(group)) {
            return;
        }

        if(player == null) {
            return;
        }

        String UUID = player.getUniqueId().toString();

        @SuppressWarnings("unchecked")
        List<String> playerList = (List<String>) config.getConfig().getList("Groups." + group + ".players");
        System.out.println(playerList);

        if (playerList == null) {
            System.out.println("PlayerList equals null");
            return;
        }
        playerList.add(UUID);
        config.getConfig().set("Groups." + group + ".players", playerList);
        config.save();
    }

    public static void removePlayerFromGroup(Player player, String group) {

        String UUID = player.getUniqueId().toString();

        @SuppressWarnings("unchecked")
        List<String> playerList = (List<String>) config.getConfig().getList("Groups." + group + ".players");

        if(!playerList.contains(UUID)) {
            return;
        }

        playerList.remove(UUID);
        config.getConfig().set("Groups." + group + ".players", playerList);
        config.save();
    }

    public static boolean getPlayerGroup(Player player, String group) {
        String UUID = player.getUniqueId().toString();

        @SuppressWarnings("unchecked")
        List<String> playerList = (List<String>) config.getConfig().getList("Groups." + group + ".players");

        if(playerList.contains(UUID)) {
            return true;
        } else {
            return false;
        }
    }

    public static void addPermission(String group, String permission) {
        System.out.println("[DEBUG] Group: " + group + " Permission: " + permission);
        @SuppressWarnings("unchecked")
        List<String> groupPermissionList = (List<String>) config.getConfig().getList("Groups." + group + ".permissions");

        if(config.getConfig().get("Groups." + group) == null) {
            return;
        }

        groupPermissionList.add(permission);
        config.getConfig().set("Groups." + group + ".permissions", groupPermissionList);
        config.save();

    }

    public static List<String> listPlayers(String group) {
        @SuppressWarnings("unchecked")
        List<String> playerList = (List<String>) config.getConfig().getList("Groups." + group + ".players");

        return playerList;
    }

    public static void copyDefaultGroup() {

        if(config.getConfig().get("Groups.default") != null) {
            return;
        }

        config.getConfig().set("Groups.default.level", 1);
        config.save();
    }

    public static void removePermission(String group, String permission) {
        List<String> groupPermissionList = (List<String>) config.getConfig().getList("Groups." + group + ".permissions");

        if(config.getConfig().get("Groups." + group) == null) {
            return;
        }

        if(!groupPermissionList.contains(permission)) {
            return;
        }

        groupPermissionList.remove(permission);
        config.getConfig().set("Groups." + group + ".permissions", groupPermissionList);
        config.save();
    }

    public static boolean getPermission(String group, String permission) {
        List<String> groupPermissionList = (List<String>) config.getConfig().getList("Groups." + group + ".permissions");

        if(config.getConfig().get("Groups." + group) == null) {
            return false;
        }

        if(groupPermissionList.contains(permission)) {
            return true;

        } else {
            return false;
        }

    }

    public static void removeGroup(String name) {

        if(config.getConfig().get("Groups." + name) == null) {
            return;
        }

        config.getConfig().set("Groups." + name, null);
        config.save();

    }

    public static String listGroups() {

        if(config.getConfig().get("Groups") == null) {
            return "error";
        }

        String groups = config.getConfig().getConfigurationSection("Groups").getKeys(false).toString();
        return groups;
    }

    public static boolean getGroup(String name) {

        if(config.getConfig().get("Groups." + name) != null) {
            return true;

        } else {

            return false;
        }
    }

    public static String listPermissions(String group) {

        if(config.getConfig().get("Groups." + group) == null) {
            return "error";
        }

        List<String> groupPermissionList = (List<String>) config.getConfig().getList("Groups." + group + ".permissions");

        String permissions = groupPermissionList.toString();
        return permissions;
    }

}
