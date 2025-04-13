package app.sonic.test.playlist;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static app.sonic.TestData.DESCRIPTION;
import static app.sonic.TestData.PLAYLIST_NAME;
import static app.sonic.utils.DataUtil.getCurrentDate;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@Epic("Manage Playlist")
@Feature("User Playlist")
@Story("API: Get User Playlist Endpoint")
@Test
public class CreatePlaylistTest extends PlaylistBase {

    String playlistId;
    boolean hasAfterMethod;

    @Description("As an API client, I should be able to create a new playlist.")
    public void createPlaylist() {
        hasAfterMethod = true;
        playlistId = createPlaylist(sonic.getConfig().getUserId(), PLAYLIST_NAME + getCurrentDate("yyyyMMdd"),
                DESCRIPTION, false)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue())
                .extract().response().path("id");
    }

    @Description("As an API client, I should not be able to create a playlist without required fields.")
    public void createPlaylistWithoutRequiredFields() {
        hasAfterMethod = false;
        createPlaylist(sonic.getConfig().getUserId(), "", "", false)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("Missing required field: name"));
    }

    @AfterMethod
    public void unfollowPlaylist() {
        if (hasAfterMethod) {
            unfollowPlaylist(playlistId)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK);
        }
    }
}