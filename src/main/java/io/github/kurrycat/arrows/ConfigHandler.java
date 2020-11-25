package io.github.kurrycat.arrows;

import processing.data.JSONObject;

import java.io.*;
import java.lang.reflect.Field;

/**
 * Handler class for the config file
 */
public class ConfigHandler {
	/**
	 * The config File object
	 */
	public static File configFile;
	/**
	 * The config filename
	 */
	public static final String configFileName = "config.json";
	/**
	 * The config JSONObject
	 */
	public static JSONObject config;

	/**
	 * Writes {@link #config} to {@link #configFile}
	 */
	public static void write() {
		try {
			PrintWriter writer = new PrintWriter(configFile);
			writer.write(config.toString());
			writer.flush();
			writer.close();
		} catch (FileNotFoundException ignored) {
		}
	}

	/**
	 * Initializes {@link #config} and {@link #configFile} and creates {@link #configFile} if it doesn't exist.
	 */
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

	/**
	 * Loads all fields from {@link #config} and sets them in {@link Settings}
	 */
	public static void loadConfig() {
		if (config == null) return;
		try {
			Field[] fields = Settings.class.getDeclaredFields();
			for (Field field : fields) {
				Object value = config.get(field.getName());
				if (value == null) continue;
				if (field.getType().isEnum()) {
					Object[] constants = field.getType().getEnumConstants();
					for (Object constant : constants) {
						if (constant.toString().equals(value)) {
							field.set(Settings.class, constant);
						}
					}
				} else
					field.set(Settings.class, value);
			}
		} catch (IllegalAccessException ignored) {
		}
	}

	/**
	 * Sets all fields from {@link Settings} into {@link #config} and executes {@link #write()}
	 */
	public static void saveConfig() {
		if (config == null) return;

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
