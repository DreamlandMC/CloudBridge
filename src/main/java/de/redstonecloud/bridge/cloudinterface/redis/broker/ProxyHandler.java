package de.redstonecloud.bridge.cloudinterface.redis.broker;

import de.redstonecloud.api.redis.broker.packet.Packet;
import de.redstonecloud.api.redis.broker.packet.defaults.server.RemoveServerPacket;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;

public class ProxyHandler {

    public static void handle(Packet packet) {
        if (packet instanceof RemoveServerPacket pk)
            on(pk);
    }

    public static void on(RemoveServerPacket packet) {
        CloudInterface.getExecutor().removeServer(packet.getServer());
    }
}

