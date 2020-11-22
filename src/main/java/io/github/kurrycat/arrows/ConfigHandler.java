package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.Game;
import processing.data.JSONObject;

import java.io.*;
import java.lang.reflect.Field;

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
			configFile = new File(ConfigHandler.class.getProtectionDomain().getCodeSource().getLocation().getFile());
			if (!configFile.isDirectory()) configFile = configFile.getParentFile();
			configFile = new File(configFile.getAbsolutePath() + File.separator + configFileName);

			if (!configFile.exists()) {
				configFile.createNewFile();
			}
			System.out.println(configFile.getAbsolutePath());
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
		} catch (IOException e) {
			Sketch.message(ConfigHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + configFileName);
		}
	}

	public static void loadConfig() {
		if (config == null) return;
		Game.instance.highscore = config.getInt("highscore", 0);
		try {
			Field[] fields = Settings.class.getDeclaredFields();
			for (Field field : fields) {
				Object value = config.get(field.getName());
				if (value == null) value = field.get(Settings.class);
				field.set(Settings.class, value);
			}
		} catch (IllegalAccessException ignored) {
		}
	}

	public static void saveConfig() {
		if (config == null) return;
		config.setInt("highscore", Game.instance.highscore);

		try {
			Field[] fields = Settings.class.getDeclaredFields();
			for (Field field : fields) {
				config.put(field.getName(), field.get(Settings.class));
			}
		} catch (IllegalAccessException ignored) {
		}

		write();
	}
}
