package app.sonic.test.playlist;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;

@Epic("Manage Playlist")
@Feature("Create Playlist")
@Story("API: Create Playlist Endpoint")
@Test
public class CreatePlaylistTest extends PlaylistBase {

    @Description("As an API client, I should not be able to create a playlist without required fields")
    public void createPlaylistWithoutRequiredFields() {
        createPlaylist("", "", false)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("Bad uri"));
    }
}