package sudark.qqbot;

import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AllowList {
    File dataFolder = Bukkit.getPluginsFolder();
    File dataFile = new File(dataFolder, "allowlist.csv");

    //检查文件是否存在
    public void checkFile() {

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //检查是否在白名单
    public String checkAllowListByUUID(String uuid,String name) {

        List<List<String>> data = readCSV(dataFile);

        for (List<String> row : data) {
            if (row.get(0).equals(uuid)) {
                if(!row.get(2).equals(name)){
                    row.set(2,name);
                    Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage("您的旧名字 [§e"+name + "§f] 已更新");
                    writeCSV(dataFile,data);
                }
                return row.get(1);
            }
        }

        return null;
    }

    public String checkAllowListByName(String name) {

        List<List<String>> data = readCSV(dataFile);

        for (List<String> row : data) {
            if (row.get(2).equals(name)) {
                return row.get(1);
            }
        }

        return null;
    }

    //读文件
    public static List<List<String>> readCSV(File file) {
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                List<String> row = new ArrayList<>();
                for (String column : columns) {
                    row.add(column.trim());
                }
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    //写文件
    public static void writeCSV(File file, List<List<String>> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (List<String> row : data) {

                String line = String.join(",", row);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
