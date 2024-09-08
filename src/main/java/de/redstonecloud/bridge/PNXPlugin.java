package de.redstonecloud.bridge;

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
        cloudInterface.start();
    }

    @Override
    public void onDisable() {
        cloudInterface.shutdown();
    }
}