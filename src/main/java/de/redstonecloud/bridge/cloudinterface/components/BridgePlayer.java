package de.redstonecloud.bridge.cloudinterface.components;

import com.google.common.net.HostAndPort;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.redstonecloud.api.components.ICloudPlayer;
import de.redstonecloud.api.components.ICloudServer;
import de.redstonecloud.api.components.ServerStatus;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BridgePlayer implements ICloudPlayer {
    private HostAndPort address;
    private BridgeServer network;
    private BridgeServer server;
    private String uuid;
    private String name;
    private JsonElement customData;

    public static BridgePlayer readFromCache(String uuid) {
        String cachedData = CloudInterface.getCache().get("player:" + uuid);

        if(cachedData == null ||cachedData.isEmpty()) return null;

        JsonObject json = CloudInterface.GSON.fromJson(cachedData, JsonObject.class);

        BridgePlayer server = BridgePlayer.builder()
                .uuid(json.get("uuid").getAsString())
                .name(json.get("name").getAsString())
                .address(HostAndPort.fromString(json.get("address").getAsString()))
                .network(BridgeServer.readFromCache(json.get("network").getAsString()))
                .server(BridgeServer.readFromCache(json.get("server").getAsString()))
                .customData(new JsonParser().parse(json.get("customData").getAsString()))
                .build();

        return server;
    }

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
    public String getUUID() {
        return "";
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
        return "";
    }
}
