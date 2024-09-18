package de.redstonecloud.bridge.cloudinterface.netty;

import de.pierreschwang.nettypacket.event.PacketSubscriber;
import de.redstonecloud.api.netty.client.NettyClient;
import de.redstonecloud.api.netty.packet.server.RemoveServerPacket;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProxyHandler {
    protected final NettyClient client;

    @PacketSubscriber
    public void on(RemoveServerPacket packet) {
        CloudInterface.getExecutor().removeServer(packet.getServer());
    }
}

