package de.redstonecloud.bridge.platform.pnx;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import de.redstonecloud.api.components.ICloudPlayer;
import de.redstonecloud.bridge.cloudinterface.components.BridgeExecutor;

import java.util.Objects;

public class PNXExecutor implements BridgeExecutor {
    private static Server server = Server.getInstance();

    public void addServer() {}

    public void removeServer() {}

    public Player getPlayerByCloudPlayer(ICloudPlayer player) {
        return server.getOnlinePlayers().values().stream().filter(p -> p.getName().equals(player.getName())).toArray(Player[]::new)[0];
    }

    public void sendMessage(ICloudPlayer cloudPlayer, String message) {
        Objects.requireNonNull(getPlayerByCloudPlayer(cloudPlayer)).sendMessage(message);
    }

    @Override
    public void kick(ICloudPlayer player) {
        Objects.requireNonNull(getPlayerByCloudPlayer(player)).kick();
    }

    @Override
    public void kick(ICloudPlayer player, String reason) {
        Objects.requireNonNull(getPlayerByCloudPlayer(player)).kick(reason, false);
    }

    @Override
    public void runDelayed(Runnable code, int tickDelay) {
        server.getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {
                code.run();
            }
        }, tickDelay);
    }
}
