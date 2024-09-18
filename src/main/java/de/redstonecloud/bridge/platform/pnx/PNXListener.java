package de.redstonecloud.bridge.platform.pnx;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectedEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class PNXListener implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent ev) {
        Player player = ev.getPlayer();

        CloudInterface.getInstance().playerLogin(player.getName(), player.getUniqueId().toString(), player.getAddress());
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent ev) {
        Player player = ev.getPlayer();

        CloudInterface.getInstance().playerDisconnect(player.getUniqueId().toString());
    }
}
