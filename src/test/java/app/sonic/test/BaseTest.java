package app.sonic.test;

import app.sonic.Sonic;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import lombok.Getter;

import java.util.Base64;

import static io.restassured.RestAssured.*;

public class BaseTest {

    public Sonic sonic;

    @Getter
    public static String accessToken;

    public void setup() {
        sonic = new Sonic();
        createToken();
        defaultParser = Parser.JSON;
    }

    public void createToken() {
        baseURI = sonic.getConfig().getAuthUri();
        basePath = "";

        String credentials = sonic.getConfig().getClientId() + ":" + sonic.getConfig().getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        accessToken =
                given().
                        header("Authorization", "Basic " + encodedCredentials).
                        contentType("application/x-www-form-urlencoded; charset=utf-8").
                        formParam("grant_type", "refresh_token").
                        formParam("refresh_token", sonic.getConfig().getRefreshToken()).
                        when().post().
                        then().
                        extract().response().
                        path("access_token");
    }

    public Header getAuthHeader() {
        return new Header("Authorization", "Bearer " + accessToken);
    }
}