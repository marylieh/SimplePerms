package de.***REMOVED***.simpleperms.commands.override;

import de.***REMOVED***.simpleperms.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class DeopCommand implements Listener {

    @EventHandler
    public void handleCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        if(message.equalsIgnoreCase("/deop")) {
            event.setCancelled(true);
            Main.getInstance().getPermissionManager().removePermission(player, "*");
            Main.getInstance().getPermissionManager().savePlayerPermissions(player, "*");
            player.sendMessage(Main.getInstance().getPrefix() + "§4You have been successfully deoped!");
        }
    }
}
