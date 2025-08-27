package api;

import client.Order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {

    public static Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    public static Response getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
    }

    public static Response getOrdersList(Integer courierId) {
        return given()
                .header("Content-type", "application/json")
                .param("courierId", courierId)
                .when()
                .get("/api/v1/orders");
    }
}
