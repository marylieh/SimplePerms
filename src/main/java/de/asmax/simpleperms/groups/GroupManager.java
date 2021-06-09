package de.***REMOVED***.simpleperms.groups;

import de.***REMOVED***.simpleperms.Main;
import de.***REMOVED***.simpleperms.utils.Config;

import java.util.List;

public class GroupManager {

    Config config = Main.getInstance().getConfiguration();

    private String name;

    @SuppressWarnings("unchecked")
    private List<String> groupPermissionList = (List<String>) config.getConfig().getList("Groups." + name + ".permissions");

    public void addGroup(String name, int level) {
        config.getConfig().set("Groups." + name + ".level", level);
        config.save();
    }

    public void addPermission(String name, String permission) {

        if(config.getConfig().get("Groups." + name) == null) {
            return;
        }

        this.name = name;

        groupPermissionList.add(permission);
        config.getConfig().set("Groups." + name + ".permissions", groupPermissionList);
        config.save();

    }

    public void removePermission(String name, String permission) {

        if(config.getConfig().get("Groups." + name) == null) {
            return;
        }

        if(!groupPermissionList.contains(permission)) {
            return;
        }

        this.name = name;

        groupPermissionList.remove(permission);
        config.getConfig().set("Groups." + name + ".permissions", groupPermissionList);
        config.save();
    }

    public boolean getPermission(String name, String permission) {

        if(config.getConfig().get("Groups." + name) == null) {
            return false;
        }

        this.name = name;

        if(groupPermissionList.contains(permission)) {
            return true;

        } else {
            return false;
        }

    }

    public void removeGroup(String name) {

        if(config.getConfig().get("Groups." + name) == null) {
            return;
        }

        config.getConfig().set("Groups." + name, null);
        config.save();

    }

    public String listGroups() {

        if(config.getConfig().get("Groups") == null) {
            return "error";
        }

        String groups = config.getConfig().getConfigurationSection("Groups").getKeys(false).toString();
        return groups;
    }

    public boolean getGroup(String name) {

        if(config.getConfig().get("Groups." + name) != null) {
            return true;

        } else {

            return false;
        }
    }

}
