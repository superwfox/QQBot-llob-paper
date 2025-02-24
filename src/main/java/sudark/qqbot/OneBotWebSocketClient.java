package sudark.qqbot;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OneBotWebSocketClient extends WebSocketClient {
    QQBot qqBot = (QQBot) Bukkit.getPluginManager().getPlugin("QQBot");
    AllowList al = new AllowList();
    private String msg;
    File dataFolder = Bukkit.getPluginsFolder();
    File dataFile = new File(dataFolder, "allowlist.csv");
    Set<String> confirmList = null;

    public OneBotWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public void sendG(String message) {
        JSONObject connected = new JSONObject();
        JSONObject connectedi = new JSONObject();
        connectedi.put("group_id", "808298146");
        connectedi.put("message", message);
        connectedi.put("auto_escape", "false");
        connected.put("action", "send_group_msg");
        connected.put("params", connectedi);

        try {
            this.send(connected.toString());
        } catch (Exception var5) {
            Exception e = var5;
            e.printStackTrace();
        }

    }

    public void sendP(String user, String message) {
        JSONObject connected = new JSONObject();
        JSONObject connectedi = new JSONObject();
        connectedi.put("user_id", user);
        connectedi.put("message", message);
        connectedi.put("auto_escape", "false");
        connected.put("action", "send_private_msg");
        connected.put("params", connectedi);

        try {
            this.send(connected.toString());
        } catch (Exception var6) {
            Exception e = var6;
            e.printStackTrace();
        }

    }

    public void sendF(String content, String content2) {
        JSONObject json = new JSONObject();
        JSONObject inner = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject data2 = new JSONObject();
        JSONObject type = new JSONObject();
        JSONObject type2 = new JSONObject();
        JSONObject typeo = new JSONObject();
        JSONObject typeo2 = new JSONObject();
        JSONObject datai = new JSONObject();
        JSONObject datai2 = new JSONObject();
        JSONArray msg = new JSONArray();
        JSONArray contents = new JSONArray();
        JSONArray contents2 = new JSONArray();
        data.put("text", content);
        type.put("type", "text");
        type.put("data", data);
        contents.add(type);
        datai.put("nickname", "DOFES");
        datai.put("user_id", "2963502563");
        datai.put("content", contents);
        typeo.put("type", "node");
        typeo.put("data", datai);
        data2.put("text", content2);
        type2.put("type", "text");
        type2.put("data", data2);
        contents2.add(type2);
        datai2.put("nickname", "DEKUSE");
        datai2.put("user_id", "2963502563");
        datai2.put("content", contents2);
        typeo2.put("type", "node");
        typeo2.put("data", datai2);
        msg.add(typeo);
        msg.add(typeo2);
        inner.put("messages", msg);
        inner.put("group_id", "808298146");
        inner.put("auto_escape", "false");
        json.put("action", "send_group_forward_msg");
        json.put("params", inner);

        try {
            this.send(json.toString());
        } catch (Exception var17) {
            Exception e = var17;
            e.printStackTrace();
        }

    }

    public void onOpen(ServerHandshake serverHandshake) {
        Bukkit.getLogger().info("\u001b[33;1m\u001b[1m 机器人已连接\u001b[0m");
        this.sendG(" 服务器已启动 ！ ");
    }

    public void onMessage(String s) {
        JSONObject json = JSONObject.fromObject(s);
        String msg;
        String qq = "";
        if (json.containsKey("user_id") && json.containsKey("raw_message") && json.getString("user_id").equals("2054565750")) {
            qq = json.getString("user_id");
        }
        if (json.containsKey("raw_message") && qq.equals("2054565750")) {
            msg = json.getString("raw_message");
            if (msg.startsWith("c/")) {
                final String f = msg.substring(2);
                Bukkit.getScheduler().runTask(this.qqBot, new Runnable() {
                    public void run() {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), f);
                    }
                });
                this.sendP(json.getString("user_id"), "执行成功");
                return;
            }
        }

        if (json.containsKey("group_id") && json.getString("group_id").equals("808298146")) {
            switch (json.getString("post_type")) {
                case "message":
                    JSONObject sender = json.getJSONObject("sender");
                    String card = sender.optString("card", (String) null);
                    if (card == null || card.equals("")) {
                        card = sender.getString("nickname");
                    }

                    msg = json.getString("raw_message").replaceAll("\\[CQ:face,id=\\d+]", "[§b表情§f]").replaceAll("\\[CQ:image,file=[^]]+]", "[§b图片§f]").replaceAll("\\[CQ:at,qq=\\d+]", "§6§l@§r§f").replaceAll("\\[CQ:reply,id=[^]]+]", "").replaceAll("\\[CQ:video,id=[^]]+]", "[§b视频§f]");
                    if (msg.equalsIgnoreCase("cf") || msg.contains("有人吗")) {
                        if (Bukkit.getOnlinePlayers().isEmpty()) {
                            this.sendG("服里没人");
                        } else {
                            this.sendG("服里有" + n2c(Bukkit.getOnlinePlayers().size()) + "个人");
                        }

                        return;
                    }

                    if (msg.equals("有那几个人") || msg.equals("有哪几个人") || msg.equals("都有谁") || msg.equals("查服")) {
                        if (Bukkit.getOnlinePlayers().size() == 0) {
                            this.sendG("服里没人啊");
                        } else {
                            String list = "";

                            String name;
                            for (Iterator var9 = Bukkit.getOnlinePlayers().iterator(); var9.hasNext(); list = list + "·" + name + "\n") {
                                Player player = (Player) var9.next();
                                name = player.getName();
                            }

                            list = list + "\n_________________\n以上玩家列表 共" + n2c(Bukkit.getOnlinePlayers().size()) + "人";
                            Runtime runtime = Runtime.getRuntime();
                            long totalMemory = runtime.totalMemory() / 1048576L;
                            long freeMemory = runtime.freeMemory() / 1048576L;
                            long usedMemory = totalMemory - freeMemory;
                            double mspt = Bukkit.getServer().getAverageTickTime();
                            String tps = String.format("%.2f", Math.min(1000.0 / mspt, 20.0));
                            String smspt = String.format("%.3f", mspt);
                            String list2 = "服务器 用内存 " + usedMemory + " MB";
                            list2 = list2 + "\n服务器 余内存 " + freeMemory + " MB";
                            list2 = list2 + "\n\nTPS  " + tps + " / 20.00";
                            list2 = list2 + "\nMSPT " + smspt;
                            if (1000.0 / mspt > 19.5) {
                                list2 = list2 + "\n·\ud83c\udf40 服务器 状态极佳";
                            } else if (1000.0 / mspt < 14.0) {
                                list2 = list2 + "\n·\ud83d\udd25 服务器 状态较差";
                            }

                            this.sendF(list, list2);
                        }

                        return;
                    }

                    if (msg.contains("绑定 ")) {
                        String GameName = msg.split(" ")[1];

                        List<List<String>> data = al.readCSV(dataFile);

                        for (List<String> row : data) {
                            if (row.get(1).equals(qq)) {
                                if (row.get(2).equals("+")) break;
                                this.sendG("您的QQ已经绑定账号\n [" + row.get(2) + "]\n需要换绑请发 是的");
                                confirmList.add(qq);
                                return;
                            }
                        }

                        data.add(Arrays.asList("+", qq, GameName, "+"));
                        al.writeCSV(dataFile, data);
                        this.sendG("绑定成功");

                    }

                    if (confirmList.contains(qq)) {
                        if (msg.equals("是的")) {
                            List<List<String>> data = al.readCSV(dataFile);
                            for (List<String> row : data) {
                                if (row.get(1).equals(qq)) {
                                    row.set(2, "+");
                                    al.writeCSV(dataFile, data);
                                    break;
                                }
                            }
                        }
                        confirmList.remove(qq);
                    }

                    if (msg.length() > 25) {
                        msg = msg.substring(0, 25) + "§7...";
                        Bukkit.broadcastMessage("[§e" + card + "§f] " + msg);
                    } else {
                        Bukkit.broadcastMessage("[§e" + card + "§f] " + msg);
                    }

                case "notice": {

                    if (json.containsKey("post_type") && json.get("post_type").equals("group_decrease")) {
                        qq = json.get("user_id").toString();

                        List<List<String>> data = al.readCSV(dataFile);

                        for (List<String> row : data) {
                            if (row.get(1).equals(qq)) {
                                data.remove(row);
                                al.writeCSV(dataFile, data);
                                sendG("已将 " + row.get(2) + " 从白名单中移除");
                            }
                        }
                    }

                }
            }


        }

    }

    public void onClose(int i, String s, boolean b) {
    }

    public void onError(Exception e) {
    }

    public static String n2c(int n) {
        String[] units = new String[]{"", "十"};
        String[] digits = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        if (n == 0) {
            return "零";
        } else if (n == 2) {
            return "两";
        } else {
            StringBuilder sb = new StringBuilder();
            int ten = n / 10;
            int one = n % 10;
            if (ten > 1) {
                sb.append(digits[ten]);
                sb.append(units[1]);
            }

            if (one > 0) {
                sb.append(digits[one]);
            }

            return sb.toString();
        }
    }
}

