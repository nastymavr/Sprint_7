package api;

import client.Courier;
import client.CourierLogin;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {

    @Step("Создание курьера с данными: {courier}")
    public static Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Логин курьера с данными: {loginData}")
    public static Response loginCourier(CourierLogin loginData) {
        return given()
                .header("Content-type", "application/json")
                .body(loginData)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Логин курьера с логином: {login} и паролем: {password}")
    public static Response loginCourier(String login, String password) {
        return loginCourier(new CourierLogin(login, password));
    }

    @Step("Удаление курьера с ID: {courierId}")
    public static Response deleteCourier(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}
