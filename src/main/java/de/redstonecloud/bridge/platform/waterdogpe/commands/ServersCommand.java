package de.redstonecloud.bridge.platform.waterdogpe.commands;

import de.redstonecloud.api.encryption.KeyManager;
import de.redstonecloud.api.redis.cache.Cache;
import de.redstonecloud.bridge.cloudinterface.CloudInterface;
import de.redstonecloud.bridge.cloudinterface.components.BridgeServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.TranslationContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

public class ServersCommand extends Command {

    public ServersCommand() {
        super("servers", CommandSettings.builder()
                .setDescription("waterdog.command.list.description")
                .setUsageMessage("waterdog.command.list.usage")
                .setPermission("waterdog.command.list.permission")
                .build());
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        if (args.length >= 1) {
            BridgeServer srv = CloudInterface.getExecutor().determineServer(args[0]);
            sender.sendMessage(srv == null ? "§cServer not found!" : this.buildServerList(sender.getProxy().getServerInfo(srv.getName())));
            return true;
        }

        Set<ServerInfo> cs = new HashSet<>();

        for(String s : new Cache().keys("server:*")) {
            BridgeServer srv = CloudInterface.getExecutor().determineServer(s.replace("server:", ""));
            cs.add(sender.getProxy().getServerInfo(srv.getName()));
        }

        List<ServerInfo> servers = new ArrayList<>(cs);
        servers.sort(Comparator.comparing(ServerInfo::getServerName));

        StringBuilder builder = new StringBuilder("§aShowing all servers:\n");
        for (ServerInfo serverInfo : servers) {
            builder.append(this.buildServerList(serverInfo)).append("\n§r");
        }

        builder.append("§rTotal online players: ").append(sender.getProxy().getPlayers().size());
        sender.sendMessage(builder.toString());
        return true;
    }

    private String buildServerList(ServerInfo serverInfo) {
        StringJoiner joiner = new StringJoiner(",");
        for (ProxiedPlayer player : serverInfo.getPlayers()) {
            joiner.add(player.getName());
        }

        return new TranslationContainer("waterdog.command.list.format",
                serverInfo.getServerName(),
                String.valueOf(serverInfo.getPlayers().size()),
                joiner.toString()
        ).getTranslated();
    }
}
