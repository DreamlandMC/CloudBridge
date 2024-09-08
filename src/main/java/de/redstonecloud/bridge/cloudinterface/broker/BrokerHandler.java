package de.redstonecloud.bridge.cloudinterface.broker;

import de.redstonecloud.api.components.Request;
import de.redstonecloud.api.redis.broker.message.Message;

public class BrokerHandler {
    public static void handle(Message message) {
        String[] args = message.getArguments();

        switch(args[0]) {
            //TODO: HANDLE MESSAGES
        }
    }
}
