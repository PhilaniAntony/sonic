package app.sonic.test.playlist;

import app.sonic.test.BaseTest;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PlaylistBase extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        super.setup();
        setupPlaylistEnvironment();
    }

    public Response createPlaylist(String name, String description, boolean isPublic) {
        return given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .body(createUpdatePlaylistPayload(name, description, isPublic).toString())
                .post()
                .then().extract().response();
    }

    public Response updatePlaylistDetails(String playlistId, JSONObject payload) {
        return given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .put("/playlists/" + playlistId)
                .then().extract().response();
    }

    public JSONObject createUpdatePlaylistPayload(String name, String description, boolean isPublic) {
        JSONObject playlist = new JSONObject();

        playlist.put("name", name);
        playlist.put("description", description);
        playlist.put("public", isPublic);

        return playlist;
    }

    public Response getPlaylistDetails(String playlistId) {
        return given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .when()
                .get("/playlists/" + playlistId)
                .then()
                .extract().response();
    }

    public Response getUserPlaylists(String userId) {
        return given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .when()
                .get("/users/" + userId + "/playlists")
                .then()
                .extract().response();
    }

    public ResponseSpecification getPlaylistDetailsExpectedResponseSpec(String playlistId) {
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
                .expectBody("href", nullValue())
                .expectBody("total", notNullValue())
                .rootPath("tracks")
                .expectBody("href", notNullValue())
                .expectBody("items", notNullValue())
                .expectBody("limit", notNullValue())
                .expectBody("next", nullValue())
                .expectBody("offset", notNullValue())
                .expectBody("previous", nullValue())
                .expectBody("total", notNullValue())
                .build();
    }

    public Response addTracksToPlaylist(String playlistId, List<String> tracks, List<String> episodes, int position) {
        return given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .body(tracklistPayload(tracks, episodes, position).toString())
                .post("/playlists/" + playlistId + "/tracks")
                .then()
                .extract().response();
    }

    public JSONObject tracklistPayload(List<String> tracks, List<String> episodes, int position) {
        JSONObject payload = new JSONObject();
        JSONArray tracksList = new JSONArray();

        if (!tracks.isEmpty() && !episodes.isEmpty()) {
            for (String track : tracks) {
                String trackName = "spotify:track:" + track;
                tracksList.add(trackName);
            }
            for (String episode : episodes) {
                String trackName = "spotify:episode" + episode;
                tracksList.add(trackName);
            }
        } else {
            payload.put("uris", new JSONArray());
        }

        payload.put("uris", tracksList);
        payload.put("position", position);
        return payload;
    }

    public void setupPlaylistEnvironment() {
        RestAssured.baseURI = sonic.getConfig().getUri();
        RestAssured.basePath = sonic.getConfig().getPath();
    }
}