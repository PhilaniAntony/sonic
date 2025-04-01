package app.sonic.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;

@Epic("Manage Airlines")
@Feature("Add Create And Get Endpoints")
@Story("Add 'Create' and 'Get' Endpoints")
@Test
public class SampleTest extends BaseTest {

    @BeforeMethod
    public void setupAuthHeaders() {
        createToken();
    }

    @Description("As an API client, I should be able to retrieve a playlist's details.")
    public void viewPlaylistDetailsTest() {
        getPlaylistDetails(sonic.getConfig().getPlaylistId())
                .then()
                .log().all()
                .assertThat()
                .spec(getAllPlaylistExpectedResponseSpec(""));
    }
}