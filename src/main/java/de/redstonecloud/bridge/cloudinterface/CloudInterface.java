package de.redstonecloud.bridge.cloudinterface;

import de.redstonecloud.api.redis.broker.Broker;
import de.redstonecloud.api.redis.cache.Cache;
import de.redstonecloud.bridge.cloudinterface.broker.BrokerHandler;
import lombok.Getter;

import java.io.File;

public class CloudInterface {
    private static CloudInterface INSTANCE;

    private String serverName;

    @Getter
    private Cache cache;
    @Getter
    private Broker broker;
    @Getter
    private boolean proxy;

    public static CloudInterface getInstance() {
        if(INSTANCE == null) INSTANCE = new CloudInterface();

        return INSTANCE;
    }

    private CloudInterface() {
        String workingDir = new File(System.getProperty("user.dir")).getName();

        serverName = workingDir;

        cache = new Cache();
        broker = new Broker(serverName, serverName);

        //TODO: READ CURRENT SERVER FROM CACHE & CHECK PROXY STATUS
        proxy = false;

    }

    public void start() {
        broker.listen(serverName, BrokerHandler::handle);

        //TODO: STARTUP OTHER THINGS
    }
}
