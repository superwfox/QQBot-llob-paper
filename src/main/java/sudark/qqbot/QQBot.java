package sudark.qqbot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public final class QQBot extends JavaPlugin {

    public OneBotWebSocketClient client;

    @Override
    public void onEnable() {
        this.getLogger().info("\u001b[33;1m\u001b[1m 机器人插件加载完成！ \u001b[0m");
        Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(), this);

        try {
            client = new sudark.qqbot.OneBotWebSocketClient(new URI("ws://127.0.0.1:3001"));
            client.connectBlocking();
        } catch (InterruptedException | URISyntaxException var2) {
            Exception e = var2;
            e.printStackTrace();
        }

        AllowList al = new AllowList();
        al.checkFile();

    }

    public void onDisable() {
    }

    public class PlayerChatEvent implements Listener {

        @EventHandler
        public void onChat(AsyncPlayerChatEvent event) {
            Player player = event.getPlayer();
            String msg = event.getMessage();
            if (msg.startsWith("#")) {
                String name = player.getName();
                client.sendG(name + ":\n  " + msg.substring(1));
            }

        }

        @EventHandler
        public void onJoin(PlayerPreLoginEvent event) {

            String uuids = event.getUniqueId().toString();
            String name = event.getName();
            AllowList al = new AllowList();

            if (al.checkAllowListByName(name) != null) {
                if (al.checkAllowListByUUID(uuids,name) != null || al.checkAllowListByName(name).equals("+")) {
                    return;
                }
            }

            event.setResult(PlayerPreLoginEvent.Result.KICK_WHITELIST);
            event.setKickMessage("您还没有绑定白名单\n\n请在群中发送“绑定 + 空格 + id”\n\nQQ群： 808 298 146");

        }


    }
}
