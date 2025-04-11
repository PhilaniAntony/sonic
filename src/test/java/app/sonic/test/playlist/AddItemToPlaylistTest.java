package app.sonic.test.playlist;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static app.sonic.TestData.*;
import static org.hamcrest.Matchers.is;

@Epic("Manage Playlist")
@Feature("Change Playlist Details ")
@Story("API: Change Playlist Details Endpoint")
@Test
public class AddItemToPlaylistTest extends PlaylistBase {

    @Description("As an API client, I should be able to add tracks and podcast episodes to a playlist.")
    @Test(enabled = false)
    public void addItemsToPlaylist() {
        addTracksToPlaylist(sonic.getConfig().getPlaylistId(), TRACKS, EPISODES, 1)
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Description("As an API client, I should not be able to add tracks and podcast episodes to an invalid playlist.")
    public void addItemsToPlaylistUsingInvalidId() {
        addTracksToPlaylist(INVALID_ID, TRACKS, EPISODES, 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("Invalid base62 id"));
    }

    @Description("As an API client, I should not be able to add tracks and podcast episodes using a non-existent Id.")
    public void addItemsToPlaylistUsingNonExistentId() {
        addTracksToPlaylist(NON_EXISTENT_ID, TRACKS, EPISODES, 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error.message", is("Resource not found"));
    }

    @Description("As an API client, I should not be able to add tracks and podcast episodes using an empty payload.")
    public void addItemsToPlaylistWithEmptyPayload() {
        addTracksToPlaylist(sonic.getConfig().getPlaylistId(), new ArrayList<>(), new ArrayList<>(), 1)
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("No uris provided"));
    }
}