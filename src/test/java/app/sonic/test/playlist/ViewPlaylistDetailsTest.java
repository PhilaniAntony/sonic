package app.sonic.test.playlist;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static app.sonic.TestData.INVALID_ID;
import static app.sonic.TestData.NON_EXISTENT_ID;
import static org.hamcrest.Matchers.is;

@Epic("Manage Playlist")
@Feature("Get Playlist Details")
@Story("API: Get Playlist Details Endpoints")
@Test
public class ViewPlaylistDetailsTest extends PlaylistBase {

    @Description("As an API client, I should be able to view a playlist's details.")
    public void viewPlaylistDetails() {
        getPlaylistDetails(sonic.getConfig().getPlaylistId())
                .then()
                .log().all()
                .assertThat()
                .spec(getPlaylistDetailsExpectedResponseSpec(sonic.getConfig().getPlaylistId()));
    }

    @Description("As an API client, I should not be able to view playlist details using an invalid playlist Id.")
    public void viewPlaylistUsingInvalidPlaylistId() {
        getPlaylistDetails(INVALID_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("Invalid base62 id"));
    }

    @Description("As an API client, I should not be able to view playlist details using a non-existent playlist Id.")
    public void viewPlaylistUsingNonExistentPlaylistId() {
        getPlaylistDetails(NON_EXISTENT_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error.message", is("Resource not found"));
    }
}