package api;

import client.Courier;
import client.CourierLogin;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class CourierApi {

    public static Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    public static Response loginCourier(CourierLogin loginData) {
        return given()
                .header("Content-type", "application/json")
                .body(loginData)
                .when()
                .post("/api/v1/courier/login");
    }

    public static Response loginCourier(String login, String password) {
        return loginCourier(new CourierLogin(login, password));
    }

    public static Response deleteCourier(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}
