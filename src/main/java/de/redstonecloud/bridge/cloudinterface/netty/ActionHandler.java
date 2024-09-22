package de.redstonecloud.bridge.cloudinterface.netty;

import de.pierreschwang.nettypacket.event.PacketSubscriber;
import de.redstonecloud.api.components.ServerActions;
import de.redstonecloud.api.netty.client.NettyClient;
import de.redstonecloud.api.netty.packet.server.RemoveServerPacket;
import de.redstonecloud.api.netty.packet.server.ServerAction;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import de.redstonecloud.bridge.cloudinterface.components.BridgeExecutor;
import de.redstonecloud.bridge.cloudinterface.components.BridgePlayer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionHandler {
    protected final NettyClient client;

    @PacketSubscriber
    public void on(ServerAction packet) {
        BridgeExecutor ex = CloudInterface.getExecutor();

        switch(ServerActions.valueOf(packet.getAction())) {
            case PLAYER_KICK -> ex.kick(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().getString("reason"));
            case PLAYER_SEND_MESSAGE -> ex.sendMessage(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().getString("message"));
            case PLAYER_SEND_TITLE -> ex.sendTitle(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().getString("title"));
            case PLAYER_ACTIONBAR -> ex.sendActionbar(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().getString("message"));
            case PLAYER_TOAST -> ex.sendToast(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().getString("title"), packet.getExtraData().getString("content"));

            case PLAYER_CONNECT -> ex.connect(BridgePlayer.readFromCache(packet.getPlayerUuid()), packet.getExtraData().getString("server"));
            default -> {}
        }
    }
}

