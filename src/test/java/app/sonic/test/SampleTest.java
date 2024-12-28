package app.sonic.test;

import app.sonic.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static app.sonic.TestData.AIRLINE_NAME;
import static app.sonic.TestData.COUNTRY;
import static org.hamcrest.Matchers.is;

@Epic("Manage Airlines")
@Feature("Add Create And Get Endpoints")
@Story("Add 'Create' and 'Get' Endpoints")
@Test
public class SampleTest extends BaseTest {

    @Description("As an API client, I should be able to create a new airline.")
    public void createAirline() {
        String name = AIRLINE_NAME + DataUtil.generateRandomLetters(6, true, false);

        createAirline(name, COUNTRY)
                .then()
                .log().all()
                .assertThat()
                .spec(createAirlineExpectedResponseSpec()).
                body("name", is(name)).
                body("country", is(COUNTRY));
    }

    @Description("As an API client, I should be able to retrieve all the airlines available.")
    public void retrieveAirlines() {
        getAllAirlines()
                .then()
                .log().all()
                .assertThat()
                .spec(getAllAirlineExpectedResponseSpec());
    }
}