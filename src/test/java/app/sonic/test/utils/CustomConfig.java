package app.sonic.test.utils;


import java.util.Properties;

public class CustomConfig {

    private final String customURI;
    private final String customBasePath;

    public CustomConfig(Properties properties) {
        customURI = properties.getProperty("api.uri");
        customBasePath = properties.getProperty("api.base.path");
    }

    public String getCustomURI() {
        return this.customURI;
    }

    public String getCustomBasePath() {
        return this.customBasePath;
    }
}