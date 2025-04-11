package app.sonic;

import lombok.Getter;

import java.util.Properties;

@Getter
public class Config {

    private final String uri;
    private final String path;
    private final String clientId;
    private final String clientSecret;
    private final String state;
    public String grantType;
    public String code;
    public String playlistId;
    public String userId;
    public String refreshToken;
    private final Properties properties;

    Config(Properties properties) {
        this.properties = properties;
        this.uri = properties.getProperty("base.uri");
        this.path = properties.getProperty("base.path");
        this.clientId = properties.getProperty("client.id");
        this.clientSecret = properties.getProperty("client.secret");
        this.state = properties.getProperty("state");
        this.grantType = properties.getProperty("grant.type");
        this.code = properties.getProperty("code");
        this.playlistId = properties.getProperty("playlist.id");
        this.userId = properties.getProperty("user.id");
        this.refreshToken = properties.getProperty("refresh.token");
    }
}