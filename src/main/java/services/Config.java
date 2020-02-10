package services;

import java.io.InputStream;
import java.util.Properties;

/**
 * Получаем настройки из файла app.properties
 */
public class Config {
    private static final Properties VALUES = new Properties();
    private static final Config CONFIG = new Config();

    private Config() {
        init();
    }

    public static Config getInstance() {
        return CONFIG;
    }

    private static void init() {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(
                "app_props/app.properties")) {
            VALUES.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String get(String key) {
        return VALUES.getProperty(key);
    }
}