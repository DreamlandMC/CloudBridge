package de.redstonecloud.bridge.platform.waterdogpe;

import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import de.redstonecloud.bridge.cloudinterface.components.BridgeServer;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.network.connection.handler.IForcedHostHandler;
import dev.waterdog.waterdogpe.network.connection.handler.IJoinHandler;
import dev.waterdog.waterdogpe.network.connection.handler.IReconnectHandler;
import dev.waterdog.waterdogpe.network.connection.handler.ReconnectReason;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import jline.internal.Nullable;
import lombok.NonNull;

public class WDPEHandler implements IForcedHostHandler, IReconnectHandler, IJoinHandler {

    private final ProxyServer proxyServer;

    public WDPEHandler(
            @NonNull ProxyServer proxyServer
    ) {
        this.proxyServer = proxyServer;
    }

    @Override
    public ServerInfo resolveForcedHost(@Nullable String domain, @NonNull ProxiedPlayer player) {
        BridgeServer srv = CloudInterface.getExecutor().determineServer("Server-1");
        return this.proxyServer.getServerInfo(srv.getName());
    }

    @Override
    public ServerInfo getFallbackServer(ProxiedPlayer player, ServerInfo oldServer, ReconnectReason reason, String kickMessage) {
        BridgeServer srv = CloudInterface.getExecutor().determineServer("Server-1");
        return this.proxyServer.getServerInfo(srv.getName());
    }

    @Override
    public ServerInfo getFallbackServer(
            @NonNull ProxiedPlayer player,
            @NonNull ServerInfo oldServer,
            @NonNull String kickMessage
    ) {
        BridgeServer srv = CloudInterface.getExecutor().determineServer("Server-1");
        return this.proxyServer.getServerInfo(srv.getName());

    }

    @Override
    public ServerInfo determineServer(ProxiedPlayer player) {
        BridgeServer srv = CloudInterface.getExecutor().determineServer("Server-1");
        return this.proxyServer.getServerInfo(srv.getName());

    }
}
