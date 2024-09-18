package de.redstonecloud.bridge.cloudinterface.components;

import com.google.common.net.HostAndPort;
import de.redstonecloud.api.components.ICloudPlayer;

public interface BridgeExecutor {
    void sendMessage(ICloudPlayer cloudPlayer, String message);
    void sendTitle(ICloudPlayer cloudPlayer, String title);
    void kick(ICloudPlayer player);
    void kick(ICloudPlayer player, String reason);

    void runDelayed(Runnable code, int tickDelay);

    //PROXY-ONLY:
    void addServer(String name, HostAndPort address);
    void removeServer(String name);
    boolean hasServer(String name);
    BridgeServer determineServer(String serverName);
    void connect(ICloudPlayer player, String serverName);
}
