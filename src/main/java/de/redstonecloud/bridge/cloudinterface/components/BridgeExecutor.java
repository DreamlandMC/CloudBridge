package de.redstonecloud.bridge.cloudinterface.components;

import de.redstonecloud.api.components.ICloudPlayer;

public interface BridgeExecutor {
    public void addServer();
    public void removeServer();
    public void sendMessage(ICloudPlayer cloudPlayer, String message);
    public void kick(ICloudPlayer player);
    public void kick(ICloudPlayer player, String reason);
    public void runDelayed(Runnable code, int tickDelay);
}
