package api;

import client.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {

    @Step("Создание заказа с данными: {order}")
    public static Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получение списка всех заказов")
    public static Response getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
    }

    @Step("Получение списка заказов для курьера с ID: {courierId}")
    public static Response getOrdersList(Integer courierId) {
        return given()
                .header("Content-type", "application/json")
                .param("courierId", courierId)
                .when()
                .get("/api/v1/orders");
    }
}
