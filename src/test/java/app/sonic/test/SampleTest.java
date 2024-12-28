package app.sonic.test;

import app.sonic.test.utils.DataUtil;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static app.sonic.test.TestData.AIRLINE_NAME;
import static app.sonic.test.TestData.COUNTRY;
import static org.hamcrest.Matchers.is;

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