package app.sonic.test.playlist;

import app.sonic.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static app.sonic.TestData.*;
import static org.hamcrest.Matchers.is;

@Epic("Manage Playlist")
@Feature("Change Playlist Details")
@Story("API: Change Playlist Details Endpoint")
@Test
public class ChangePlaylistDetailsTest extends PlaylistBase {

    @Description("As an API client, I should be able to update a playlist details.")
    public void changePlaylistDetails() {
        updatePlaylistDetails(sonic.getConfig().getPlaylistId(),
                createUpdatePlaylistPayload(NAME + DataUtil.generateRandomLetters(6, true, false), DESCRIPTION, false))
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Description("As an API client, I should not be able to update a playlist details using an invalid playlist Id.")
    public void changePlaylistDetailsWithInvalidPlaylistId() {
        updatePlaylistDetails(INVALID_ID,
                createUpdatePlaylistPayload(NAME + DataUtil.generateRandomLetters(6, true, false), DESCRIPTION, false))
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("Invalid base62 id"));
    }

    @Description("As an API client, I should not be able to update the details of a  playlist  using a non-existent playlist Id.")
    public void changePlaylistDetailsUsingNonExistentPlaylistId() {
        updatePlaylistDetails(NON_EXISTENT_ID,
                createUpdatePlaylistPayload(NAME + DataUtil.generateRandomLetters(6, true, false), DESCRIPTION, false))
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error.message", is("Resource not found"));
    }
}