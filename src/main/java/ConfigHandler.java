import processing.data.JSONObject;

import java.io.*;

public class ConfigHandler {
    public static File configFile;
    public static final String configFileName = "config.json";
    public static JSONObject config;

    public static void write() {
        try {
            PrintWriter writer = new PrintWriter(configFile);
            writer.write(config.toString());
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
        }
    }

    public static void init() {
        try {
            configFile = new File(ConfigHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + configFileName);
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));

            StringBuilder configString = new StringBuilder();
            String configStringLine;
            while ((configStringLine = reader.readLine()) != null) {
                configString.append(configStringLine);
            }
            try {
                config = JSONObject.parse(configString.toString());
            } catch (Exception e) {
                config = new JSONObject();
                write();
                loadConfig();
            }
        } catch (IOException ignored) {
        }
    }

    public static void loadConfig() {
        if (config == null) return;
        Game.instance.highscore = config.getInt("highscore", 0);
    }

    public static void saveConfig() {
        if (config == null) return;
        config.setInt("highscore", Game.instance.highscore);
    }
}
