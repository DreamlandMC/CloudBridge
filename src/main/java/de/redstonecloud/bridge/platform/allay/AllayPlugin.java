package de.redstonecloud.bridge.platform.allay;

import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;

public class AllayPlugin extends Plugin {
    public CloudInterface cloudInterface;

    @Override
    public void onLoad() {
        cloudInterface = CloudInterface.getInstance();
    }

    @Override
    public void onEnable() {
        cloudInterface.start(new AllayExecutor());
        Server.getInstance().getEventBus().registerListener(new AllayListener());
    }

    @Override
    public void onDisable() {
        cloudInterface.shutdown();
    }
}