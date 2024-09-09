package de.redstonecloud.bridge.platform.pnx;

import cn.nukkit.plugin.PluginBase;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;

public class PNXPlugin extends PluginBase {
    public CloudInterface cloudInterface;

    @Override
    public void onLoad() {
        cloudInterface = CloudInterface.getInstance();
    }

    @Override
    public void onEnable() {
        cloudInterface.start(new PNXExecutor());
    }

    @Override
    public void onDisable() {
        cloudInterface.shutdown();
    }
}