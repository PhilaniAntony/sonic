package app.sonic;

public class Sonic {

    private static Config config;

    public Sonic() {
        loadConfig();
    }

    private static void loadConfig() {
        PropertyLoader loader = new PropertyLoader();
        config = new Config(loader.getProperties());
    }

    public Config getConfig() {
        return config;
    }
}