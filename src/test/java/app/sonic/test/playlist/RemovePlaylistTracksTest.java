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
@Feature("Remove Playlist Tracks")
@Story("API: Remove Tracks From Playlist")
@Test
public class RemovePlaylistTracksTest extends PlaylistBase {

    @Description("As an API client, I should be able to remove tracks from a playlist.")
    public void removePlaylistTracks() {
        String playlistId = createPlaylist(sonic.getConfig().getUserId(), PLAYLIST_NAME + getCurrentDate("yyyyMMdd"),
                DESCRIPTION, false)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue())
                .extract().response().path("id");

        String snapShortId = addTracksToPlaylist(playlistId, TRACKS, 0)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().response().path("snapshot_id");

        removePlaylistTracks(playlistId, TRACKS, snapShortId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("snapshot_id", notNullValue());

        unfollowPlaylist(playlistId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Description("As an API client, I should not be able to remove tracks from a non-existent playlist.")
    public void removeTracksFromNonExistentPlaylist() {
        removePlaylistTracks(NON_EXISTENT_ID, TRACKS, "")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error.message", is("Resource not found"));
    }

    @Description("As an API client, I should be able to remove tracks from a playlist without required fields.")
    public void removeTracksWithoutRequiredFields() {
        removePlaylistTracks(sonic.getConfig().getPlaylistId(), new ArrayList<>(), "")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("No uris provided"));
    }
}
