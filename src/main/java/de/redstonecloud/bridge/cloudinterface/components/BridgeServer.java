package de.redstonecloud.bridge.cloudinterface.components;

import com.google.common.net.HostAndPort;
import com.google.gson.JsonObject;
import de.redstonecloud.api.components.ICloudServer;
import de.redstonecloud.api.components.ServerStatus;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BridgeServer implements ICloudServer {
    public static BridgeServer readFromCache(String serverName) {
        String cachedData = CloudInterface.getCache().get("server:" + serverName);

        if(cachedData == null ||cachedData.isEmpty()) return null;

        JsonObject json = CloudInterface.GSON.fromJson(cachedData, JsonObject.class);

        BridgeServer server = BridgeServer.builder()
                .template(json.get("template").getAsString())
                .name(json.get("name").getAsString())
                .port(json.get("port").getAsInt())
                .status(ServerStatus.valueOf(json.get("status").getAsString()))
                .type(json.get("type").getAsString())
                .isProxy(json.get("proxy").getAsBoolean())
                .build();

        return server;
    }

    protected String template;
    protected String name;
    protected int port;
    protected ServerStatus status;
    protected String type;
    protected long createdAt;
    protected boolean isProxy;

    @Override
    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public HostAndPort getAddress() {
        return HostAndPort.fromParts("0.0.0.0", port);
    }

    @Override
    public ServerStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ServerStatus status){}

    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public String getName() {
        return name;
    }
}
