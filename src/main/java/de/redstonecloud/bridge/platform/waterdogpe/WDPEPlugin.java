package de.redstonecloud.bridge.platform.waterdogpe;

import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import dev.waterdog.waterdogpe.plugin.Plugin;

public class WDPEPlugin extends Plugin {
    public CloudInterface cloudInterface;

    @Override
    public void onStartup() {
        cloudInterface = CloudInterface.getInstance();
    }

    @Override
    public void onEnable() {
        cloudInterface.start(new WDPEExecutor());

        WDPEHandler handlers = new WDPEHandler(this.getProxy());
        this.getProxy().setForcedHostHandler(handlers);
        this.getProxy().setJoinHandler(handlers);
        this.getProxy().setReconnectHandler(handlers);
    }

    @Override
    public void onDisable() {
        cloudInterface.shutdown();
    }
}