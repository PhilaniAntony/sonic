package app.sonic.test.playlist;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static app.sonic.TestData.*;
import static app.sonic.utils.DataUtil.getCurrentDate;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Manage Playlist")
@Feature("Add Playlist Tracks")
@Story("API: Add Tracks To Playlist")
@Test
public class AddPlaylistTracksTest extends PlaylistBase {

    @Description("As an API client, I should be able to add tracks and podcast episodes to a playlist.")
    public void addTracksToPlaylist() {
        String playlistId = createPlaylist(sonic.getConfig().getUserId(), PLAYLIST_NAME + getCurrentDate("yyyyMMdd"),
                DESCRIPTION, false)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue())
                .extract().response().path("id");

        addTracksToPlaylist(playlistId, TRACKS, 0)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("snapshot_id", notNullValue());

        unfollowPlaylist(playlistId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Description("As an API client, I should not be able to add tracks and podcast episodes to an invalid playlist.")
    public void addItemsToPlaylistUsingInvalidId() {
        addTracksToPlaylist(INVALID_ID, TRACKS, 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("Invalid base62 id"));
    }

    @Description("As an API client, I should not be able to add tracks and podcast episodes using a non-existent Id.")
    public void addItemsToPlaylistUsingNonExistentId() {
        addTracksToPlaylist(NON_EXISTENT_ID, TRACKS, 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error.message", is("Resource not found"));
    }

    @Description("As an API client, I should not be able to add tracks and podcast episodes using an empty payload.")
    public void addItemsToPlaylistWithEmptyPayload() {
        addTracksToPlaylist(sonic.getConfig().getPlaylistId(), new ArrayList<>(), 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("No uris provided"));
    }
}