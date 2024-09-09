package de.redstonecloud.bridge.cloudinterface;

import com.google.common.net.HostAndPort;
import com.google.gson.Gson;
import de.redstonecloud.api.components.ICloudServer;
import de.redstonecloud.api.components.ServerStatus;
import de.redstonecloud.api.redis.broker.Broker;
import de.redstonecloud.api.redis.broker.message.Message;
import de.redstonecloud.api.redis.cache.Cache;
import de.redstonecloud.bridge.cloudinterface.broker.BrokerHandler;
import de.redstonecloud.bridge.cloudinterface.components.BridgeExecutor;
import de.redstonecloud.bridge.cloudinterface.components.BridgeServer;
import lombok.Getter;

import java.io.File;

@Getter
public class CloudInterface {
    private static CloudInterface INSTANCE;
    public static Gson GSON = new Gson();

    private String serverName;

    @Getter
    public static Cache cache;
    public static Broker broker;
    public static boolean proxy;

    @Getter
    protected static BridgeExecutor executor;

    public static CloudInterface getInstance() {
        if(INSTANCE == null) INSTANCE = new CloudInterface();

        return INSTANCE;
    }

    private CloudInterface() {
        String workingDir = new File(System.getProperty("user.dir")).getName();

        serverName = workingDir.toUpperCase();

        cache = new Cache();
        broker = new Broker(serverName, serverName);

        BridgeServer currentServer = BridgeServer.readFromCache(serverName.toUpperCase());

        proxy = currentServer.isProxy();

    }

    public void start(BridgeExecutor executorPlugin) {
        executor = executorPlugin;
        broker.listen(serverName, BrokerHandler::handle);

        //TODO: STARTUP OTHER THINGS
        executorPlugin.runDelayed(() -> {
            new Message.Builder()
                    .setTo("cloud")
                    .append("comm:login")
                    .build()
                    .send();
        }, 20);
    }

    public void shutdown() {
        broker.getPublisher().close();
        broker.getSubscriber().close();
    }
}
