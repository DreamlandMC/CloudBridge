package de.redstonecloud.bridge.cloudinterface.redis.broker;

import de.redstonecloud.api.components.ServerActions;
import de.redstonecloud.api.redis.broker.packet.Packet;
import de.redstonecloud.api.redis.broker.packet.defaults.server.ServerActionPacket;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import de.redstonecloud.bridge.cloudinterface.components.BridgeExecutor;
import de.redstonecloud.bridge.cloudinterface.components.BridgePlayer;

public class ActionHandler {

    public static void handle(Packet packet) {
        if (packet instanceof ServerActionPacket pk)
            on(pk);
    }

    public static void on(ServerActionPacket packet) {
        BridgeExecutor ex = CloudInterface.getExecutor();

        switch(ServerActions.valueOf(packet.getAction())) {
            case PLAYER_KICK -> ex.kick(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().get("reason").getAsString());
            case PLAYER_SEND_MESSAGE -> ex.sendMessage(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().get("message").getAsString());
            case PLAYER_SEND_TITLE -> ex.sendTitle(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().get("title").getAsString());
            case PLAYER_ACTIONBAR -> ex.sendActionbar(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().get("message").getAsString());
            case PLAYER_TOAST -> ex.sendToast(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().get("title").getAsString(), packet.getExtraData().get("content").getAsString());

            case PLAYER_CONNECT -> ex.connect(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().get("server").getAsString());
            default -> {}
        }
    }
}

