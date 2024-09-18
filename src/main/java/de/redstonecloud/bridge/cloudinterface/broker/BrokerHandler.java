package de.redstonecloud.bridge.cloudinterface.broker;

import com.google.common.net.HostAndPort;
import de.redstonecloud.api.components.ICloudPlayer;
import de.redstonecloud.api.components.ICloudServer;
import de.redstonecloud.api.redis.broker.message.Message;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;

public class BrokerHandler {
    public static void handle(Message message) {
        String[] args = message.getArguments();

    }
}
