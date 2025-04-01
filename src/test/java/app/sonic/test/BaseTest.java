package app.sonic.test;

import app.sonic.Sonic;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;

import static app.sonic.TestData.REDIRECT_URI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BaseTest {

    public Sonic sonic;

    @Getter
    public static String accessToken;
    public static String refreshToken;

    public void setup() {
        sonic = new Sonic();
        baseURI = sonic.getConfig().getUri();
        basePath = sonic.getConfig().getPath();
        defaultParser = Parser.JSON;
    }

    @BeforeMethod(alwaysRun = true)
    public void setupEnvironment() {
        setup();
    }


    public void createToken() {
        Response response =
                given()
                        .baseUri("https://accounts.spotify.com")
                        .formParam("client_id", sonic.getConfig().getClientId())
                        .formParam("client_secret", sonic.getConfig().getClientSecret())
                        .formParam("grant_type", sonic.getConfig().getGrantType())
                        .formParam("code", sonic.getConfig().getCode())
                        .formParam("redirect_uri", REDIRECT_URI)
                        .when()
                        .log().all()
                        .post("/api/token")
                        .then()
                        .log().all()
                        .extract().response();

        accessToken = response.jsonPath().getString("access_token");
        refreshToken = response.jsonPath().getString("refresh_token");
    }

    public Header getAuthHeader() {
        return new Header("Authorization", "Bearer " + accessToken);
    }

    public void renewToken() {
        Response response =
                given()
                        .baseUri("https://accounts.spotify.com")
                        .formParam("client_id", sonic.getConfig().getClientId())
                        .formParam("client_secret", sonic.getConfig().getClientSecret())
                        .formParam("grant_type", sonic.getConfig().getGrantType())
                        .formParam("refresh_token", refreshToken)
                        .when()
                        .post("/api/token")
                        .then().extract().response();

        accessToken = response.jsonPath().getString("access_token");
        refreshToken = response.jsonPath().getString("refresh_token");
    }

    public Response createPlaylist(String name, String description, boolean isPublic) {
        return given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .body(createPlaylistPayload(name, description, isPublic).toString())
                .post()
                .then().extract().response();
    }

    public JSONObject createPlaylistPayload(String name, String description, boolean isPublic) {
        JSONObject playlist = new JSONObject();

        playlist.put("name", name);
        playlist.put("description", description);
        playlist.put("public", isPublic);

        return playlist;
    }

    public ResponseSpecification createPlaylistExpectedResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_CREATED)
                .build();
    }

    public Response getAllPlaylists() {
        setup();
        return given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then().extract().response();
    }

    public Response getPlaylistDetails(String playlistId) {
        setup();
        return given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .when()
                .get("/playlists/" + playlistId)
                .then().extract().response();
    }

    public ResponseSpecification getAllPlaylistExpectedResponseSpec(String playlistId) {
        return new ResponseSpecBuilder().
                expectStatusCode(HttpStatus.SC_OK)
                .expectBody("name", notNullValue())
                .expectBody("id", is(playlistId))
                .expectBody("description", notNullValue())
                .expectBody("collaborative", is(false))
                .expectBody("images", nullValue())
                .expectBody("public", notNullValue())
                .expectBody("type", notNullValue())
                .expectBody("uri", notNullValue())
                .rootPath("owner")
                .expectBody("display_name", notNullValue())
                .expectBody("external_urls.spotify", notNullValue())
                .expectBody("href", notNullValue())
                .expectBody("id", notNullValue())
                .expectBody("type", notNullValue())
                .expectBody("uri", notNullValue())
                .rootPath("followers")
                .expectBody("href", notNullValue())
                .expectBody("total", notNullValue())
                .rootPath("tracks")
                .expectBody("href", notNullValue())
                .expectBody("items", notNullValue())
                .expectBody("limit", notNullValue())
                .expectBody("next", notNullValue())
                .expectBody("offset", notNullValue())
                .expectBody("previous", notNullValue())
                .expectBody("total", notNullValue())
                .build();
    }
}