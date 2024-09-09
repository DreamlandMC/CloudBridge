package de.redstonecloud.bridge.cloudinterface.broker;

import com.google.common.net.HostAndPort;
import de.redstonecloud.api.components.ICloudPlayer;
import de.redstonecloud.api.components.ICloudServer;
import de.redstonecloud.api.redis.broker.message.Message;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;

public class BrokerHandler {
    public static void handle(Message message) {
        String[] args = message.getArguments();

        System.out.println("PROCESSING " + message.toJson());

        switch(args[0]) {
            //TODO: HANDLE MESSAGES
            case "player:message" -> {
                CloudInterface.getExecutor().sendMessage(new ICloudPlayer() {
                    @Override
                    public HostAndPort getAddress() {
                        return null;
                    }

                    @Override
                    public ICloudServer getConnectedNetwork() {
                        return null;
                    }

                    @Override
                    public ICloudServer getConnectedServer() {
                        return null;
                    }

                    @Override
                    public void sendMessage(String message) {

                    }

                    @Override
                    public void connect(String server) {

                    }

                    @Override
                    public void disconnect(String reason) {

                    }

                    @Override
                    public String getName() {
                        return args[1];
                    }
                }, args[2]);
            }
        }
    }
}
