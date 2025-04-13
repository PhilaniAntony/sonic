package app.sonic.test.playlist;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

@Epic("Manage Playlist")
@Feature("Get User's Playlists")
@Story("API: Get User's Playlists Endpoint")
@Test
public class GetUserPlaylistsTest extends PlaylistBase {

    @Description("As an API client, I should be able to retrieve a user's playlists.")
    public void viewUserPlaylists() {
        getUserPlaylists(sonic.getConfig().getUserId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Description("As an API client, I should not be able to retrieve a user's playlists.")
    public void viewUserPlaylistsUsingNonExistentUserId() {
        getUserPlaylists("12345")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("items", empty());
    }

    @Description("As an API client, I should not be able to retrieve a user's playlists without a user Id.")
    public void viewUserPlaylistsWithoutUserId() {
        getUserPlaylists("37i9dQZF1EIg4DY3QQQQQQ")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error.message", is("Resource not found"));
    }
}