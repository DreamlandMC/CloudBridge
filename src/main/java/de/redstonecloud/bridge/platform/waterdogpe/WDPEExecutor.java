package de.redstonecloud.bridge.platform.waterdogpe;

import com.google.common.net.HostAndPort;
import de.redstonecloud.api.components.ICloudPlayer;
import de.redstonecloud.bridge.cloudinterface.components.BridgeExecutor;
import de.redstonecloud.bridge.cloudinterface.components.BridgeServer;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.network.serverinfo.BedrockServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.scheduler.Task;

import java.net.InetSocketAddress;
import java.util.Objects;

public class WDPEExecutor implements BridgeExecutor {
    private static ProxyServer server = ProxyServer.getInstance();

    public void addServer(String name, HostAndPort address) {
        server.registerServerInfo(new BedrockServerInfo(name, new InetSocketAddress(address.getHost(), address.getPort()), new InetSocketAddress(address.getHost(), address.getPort())));
    }

    public void removeServer(String name) {
        server.removeServerInfo(name);
    }

    public boolean hasServer(String name) {
        return server.getServerInfo(name) != null;
    }

    @Override
    public BridgeServer determineServer(String serverName) {
        BridgeServer server = BridgeServer.readFromCache(serverName.toUpperCase());
        if(server == null) {
            if(hasServer(serverName)) removeServer(serverName);
            return null;
        }

        if(!hasServer(server.getName())) addServer(server.getName(), server.getAddress());

        return server;
    }

    public ProxiedPlayer getPlayerByCloudPlayer(ICloudPlayer player) {
        return server.getPlayers().values().stream().filter(p -> p.getName().equalsIgnoreCase(player.getName())).toArray(ProxiedPlayer[]::new)[0];
    }

    public void sendMessage(ICloudPlayer cloudPlayer, String message) {
        Objects.requireNonNull(getPlayerByCloudPlayer(cloudPlayer)).sendMessage(message);
    }

    @Override
    public void kick(ICloudPlayer player) {
        Objects.requireNonNull(getPlayerByCloudPlayer(player)).disconnect();
    }

    @Override
    public void kick(ICloudPlayer player, String reason) {
        Objects.requireNonNull(getPlayerByCloudPlayer(player)).disconnect(reason);
    }

    @Override
    public void runDelayed(Runnable code, int tickDelay) {
        server.getScheduler().scheduleDelayed(new Task() {
            @Override
            public void onRun(int i) {
                code.run();
            }

            @Override
            public void onCancel() {

            }
        }, tickDelay);
    }
}
