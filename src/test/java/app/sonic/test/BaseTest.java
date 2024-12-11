package app.sonic.test;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BaseTest {

    public Response createAirline(String name, String country) {
        return given()
                .baseUri("https://api.instantwebtools.net/v1/airlines")
                .contentType(ContentType.JSON)
                .body(createAirlinePayload(name, country).toString())
                .post()
                .then()
                .extract()
                .response();
    }

    public JSONObject createAirlinePayload(String name, String country) {
        JSONObject airline = new JSONObject();
        airline.put("name", name);
        airline.put("country", country);
        airline.put("slogan", "Let's Go Places Together");
        airline.put("head_quarters", "Cape Town");
        airline.put("website", "https://www.niceairline.com");
        airline.put("established", "1994");
        return airline;
    }

    public ResponseSpecification createAirlineExpectedResponseSpec() {
        return new ResponseSpecBuilder().
                expectStatusCode(HttpStatus.SC_OK)
                .expectBody("_id", notNullValue())
                .expectBody("name", notNullValue())
                .expectBody("country", notNullValue())
                .expectBody("slogan", notNullValue())
                .expectBody("website", notNullValue())
                .expectBody("established", notNullValue())
                .expectBody("__v", notNullValue())
                .build();
    }

    public Response getAllAirlines() {
        return given().
                baseUri("https://api.instantwebtools.net/v1/airlines").
                contentType(ContentType.JSON).
                when().
                get().
                then().
                extract().response();
    }

    public ResponseSpecification getAllAirlineExpectedResponseSpec() {
        return new ResponseSpecBuilder().
                expectStatusCode(HttpStatus.SC_OK)
                .expectBody("_id", everyItem(notNullValue()))
                .expectBody("name", everyItem(notNullValue()))
                .expectBody("country", anyOf(notNullValue(), nullValue()))
                .expectBody("slogan", anyOf(notNullValue(), nullValue()))
                .expectBody("logo", anyOf(notNullValue(), nullValue()))
                .expectBody("website", anyOf(notNullValue(), nullValue()))
                .expectBody("established", anyOf(notNullValue(), nullValue()))
                .build();
    }
}