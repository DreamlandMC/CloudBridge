package de.redstonecloud.bridge.platform.waterdogpe;

import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import de.redstonecloud.bridge.platform.waterdogpe.commands.HubCommand;
import de.redstonecloud.bridge.platform.waterdogpe.commands.SendCommand;
import de.redstonecloud.bridge.platform.waterdogpe.commands.ServerCommand;
import de.redstonecloud.bridge.platform.waterdogpe.commands.ServersCommand;
import dev.waterdog.waterdogpe.command.CommandMap;
import dev.waterdog.waterdogpe.event.EventPriority;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectedEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
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

        CommandMap map = this.getProxy().getCommandMap();
        map.unregisterCommand("server");
        map.unregisterCommand("send");

        map.registerCommand(new ServerCommand());
        map.registerCommand(new HubCommand());
        map.registerCommand(new ServersCommand());
        map.registerCommand(new SendCommand());

        this.getProxy().getEventManager().subscribe(PlayerLoginEvent.class, WDPEListener::onLogin, EventPriority.LOWEST);
        this.getProxy().getEventManager().subscribe(PlayerDisconnectedEvent.class, WDPEListener::onDisconnect, EventPriority.LOWEST);
    }

    @Override
    public void onDisable() {
        cloudInterface.shutdown();
    }
}