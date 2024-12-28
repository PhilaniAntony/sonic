package app.sonic;

import lombok.Getter;

import java.util.Properties;

@Getter
public class Config {

    private final String uri;
    private final String path;
    private final Properties properties;

    Config(Properties properties) {
        this.properties = properties;
        this.uri = properties.getProperty("base.uri");
        this.path = properties.getProperty("base.path");
    }
}