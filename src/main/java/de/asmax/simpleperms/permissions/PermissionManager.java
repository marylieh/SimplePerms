package de.***REMOVED***.simpleperms.permissions;

import de.***REMOVED***.simpleperms.Main;
import de.***REMOVED***.simpleperms.groups.GroupManager;
import de.***REMOVED***.simpleperms.utils.Config;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PermissionManager {

    Config config = Main.getInstance().getConfiguration();

    public void setPermission(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        attachment.setPermission(permission, true);
    }

    public void revokePermissions(Player player) {
        String UUID = player.getUniqueId().toString();

        if(config.getConfig().get("Player." + UUID + ".permissions") == null) {
            return;
        }

        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) config.getConfig().getList("Player." + UUID + ".permissions");
        System.out.println(permissions);

        for (int i = 0; i < permissions.size(); i++) {
            String permission = permissions.get(i);

            System.out.println(permission);

            PermissionAttachment attachment = player.addAttachment(Main.getInstance());
            attachment.setPermission(permission, true);
        }
    }

    public void revokeGroups(Player player) {
        Set<String> totalGroups = config.getConfig().getConfigurationSection("Groups").getKeys(false);
        StringBuilder groupString = new StringBuilder(totalGroups.toString());
        groupString.delete(0, 10);
        String test = groupString.toString();
        test = StringUtils.removeEnd(test, "]");
        System.out.println(test);
        test = StringUtils.deleteWhitespace(test);
        System.out.println(test);
        System.out.println(test);

        ArrayList<String> totalGroupList = new ArrayList<String>(Arrays.asList(test.split(",")));

        for(int i = 0; i < totalGroupList.size(); i++) {
            String group = totalGroupList.get(i);
            System.out.println(group);

            try {

                if(GroupManager.getPlayerGroup(player, group)) {

                    @SuppressWarnings("unchecked")
                    List<String> tempPermissionList = (List<String>) config.getConfig().getList("Groups." + group + ".permissions");

                    for (int n = 0; n < tempPermissionList.size(); n++) {
                        String permission = tempPermissionList.get(n);
                        System.out.println(permission);

                        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
                        attachment.setPermission(permission, true);

                    }

                }

            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("NullPointerException in Group Management System!");
            }
        }
    }

    public void setTempPermission(Player player, String permission, long time) {
        long now = System.currentTimeMillis();
        long dif = (time -now) / 1000;
        int availible = (int) dif*20;
        PermissionAttachment attachment = player.addAttachment(Main.getInstance(), availible);
        attachment.setPermission(permission, false);
    }

    public void removePermission(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        attachment.setPermission(permission, false);
    }

    public boolean getPermission(Player player, String permission) {
        if(player.hasPermission(permission)) {
            return true;
        } else {
            return false;
        }
    }

    public void savePlayerPermissions(Player player, String permission) {
        if(config.getConfig().get("Player." + player.getUniqueId() + ".permissions") == null) {
            ArrayList<String> tempPlayerPerms = new ArrayList<String>();
            tempPlayerPerms.add("default");
            config.getConfig().set("Player." + player.getUniqueId() + ".permissions", tempPlayerPerms);
            config.save();
        }

        @SuppressWarnings("unchecked")
        List<String> playerPerms = (List<String>) config.getConfig().getList("Player." + player.getUniqueId() + ".permissions");
        playerPerms.add(permission);
        config.getConfig().set("Player." + player.getUniqueId() + ".permissions", playerPerms);
        config.save();
    }

    public void removeSavedPlayerPermissions(Player player, String permission) {
        if(config.getConfig().get("Player." + player.getUniqueId() + ".permissions") == null) {
            System.out.println("ABORT!");
            return;
        }

        @SuppressWarnings("unchecked")
        List<String> playerPerms = (List<String>) config.getConfig().getList("Player." + player.getUniqueId() + ".permissions");
        System.out.println(playerPerms.toString());
        playerPerms.remove(permission);
        config.getConfig().set("Player." + player.getUniqueId() + ".permissions", playerPerms);
        System.out.println(playerPerms.toString());
        config.save();
    }

}
