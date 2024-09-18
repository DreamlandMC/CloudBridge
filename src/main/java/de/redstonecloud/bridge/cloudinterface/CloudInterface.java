package de.redstonecloud.bridge.cloudinterface;

import com.google.common.net.HostAndPort;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.pierreschwang.nettypacket.event.EventRegistry;
import de.redstonecloud.api.components.ICloudServer;
import de.redstonecloud.api.components.ServerStatus;
import de.redstonecloud.api.netty.NettyHelper;
import de.redstonecloud.api.netty.client.NettyClient;
import de.redstonecloud.api.netty.packet.communication.ClientAuthPacket;
import de.redstonecloud.api.netty.packet.player.PlayerConnectPacket;
import de.redstonecloud.api.netty.packet.player.PlayerDisconnectPacket;
import de.redstonecloud.api.redis.broker.Broker;
import de.redstonecloud.api.redis.broker.message.Message;
import de.redstonecloud.api.redis.cache.Cache;
import de.redstonecloud.bridge.cloudinterface.broker.BrokerHandler;
import de.redstonecloud.bridge.cloudinterface.components.BridgeExecutor;
import de.redstonecloud.bridge.cloudinterface.components.BridgeServer;
import de.redstonecloud.bridge.cloudinterface.netty.ProxyHandler;
import lombok.Getter;
import de.redstonecloud.api.netty.NettyHelper;
import de.redstonecloud.api.netty.client.NettyClient;
import de.redstonecloud.api.netty.packet.communication.ClientAuthPacket;

import java.io.File;

@Getter
public class CloudInterface {
    private static CloudInterface INSTANCE;
    public static Gson GSON = new Gson();

    private String serverName;

    @Getter
    public static Cache cache;
    @Getter
    public static NettyClient netty;
    public static Broker broker;
    public static boolean proxy;

    @Getter public static JsonObject bridgeConfig;

    @Getter
    protected static BridgeExecutor executor;

    private BridgeServer currentServerStartup;

    public static CloudInterface getInstance() {
        if(INSTANCE == null) INSTANCE = new CloudInterface();

        return INSTANCE;
    }

    private CloudInterface() {
        String workingDir = new File(System.getProperty("user.dir")).getName();
        bridgeConfig = new Gson().fromJson(System.getenv("BRIDGE_CFG"), JsonObject.class);

        serverName = workingDir.toLowerCase();

        cache = new Cache();
        broker = new Broker(serverName, serverName);

        currentServerStartup = BridgeServer.readFromCache(serverName.toUpperCase());
        netty = new NettyClient(currentServerStartup.getName().toUpperCase(), NettyHelper.constructRegistry(), new EventRegistry());
        netty.setPort(51123).bind();

        proxy = currentServerStartup.isProxy();

    }

    public void start(BridgeExecutor executorPlugin) {
        executor = executorPlugin;
        broker.listen(serverName, BrokerHandler::handle);

        if(proxy) netty.getEventRegistry().registerEvents(new ProxyHandler(netty));

        executorPlugin.runDelayed(() -> {
            ClientAuthPacket packet = new ClientAuthPacket();
            packet.setClientId(currentServerStartup.getName().toUpperCase());

            netty.sendPacket(packet);
        }, 20);
    }

    public void shutdown() {
        broker.shutdown();
    }

    public void playerLogin(String name, String uuid, String ip) {
        PlayerConnectPacket pck = new PlayerConnectPacket()
                .setPlayerName(name)
                .setUuid(uuid)
                .setIpAddress(ip)
                .setServer(currentServerStartup.getName().toUpperCase());

        netty.sendPacket(pck);
    }

    public void playerDisconnect(String uuid) {
        PlayerDisconnectPacket pck = new PlayerDisconnectPacket()
                .setUuid(uuid)
                .setServer(currentServerStartup.getName().toUpperCase());

        netty.sendPacket(pck);
    }
}
