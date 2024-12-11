package app.sonic.test;

import jdk.jfr.Description;
import org.testng.annotations.Test;

@Test
public class GetAirlinesTest extends BaseTest {

    @Description("As an API client, I should be able to retrieve all the airlines available.")
    public void retrieveAirlines() {
        getAllAirlines().
                then()
                .assertThat().
                spec(getAllAirlineExpectedResponseSpec());
    }
}