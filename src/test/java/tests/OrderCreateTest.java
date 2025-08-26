package tests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class OrderCreateTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("1. Можно указать оба цвета — BLACK и GREY")
    public void createOrderWithBlackAndGreyColors() {
        String requestBody = "{\n" +
                "  \"firstName\": \"Naruto\",\n" +
                "  \"lastName\": \"Uchiha\",\n" +
                "  \"address\": \"Konoha, 142 apt.\",\n" +
                "  \"metroStation\": \"4\",\n" +
                "  \"phone\": \"+7 800 355 35 35\",\n" +
                "  \"rentTime\": 5,\n" +
                "  \"deliveryDate\": \"2020-06-06\",\n" +
                "  \"comment\": \"Saske, come back to Konoha\",\n" +
                "  \"color\": [\"BLACK\", \"GREY\"]\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders");

        response.then().statusCode(201);
        response.then().body("track", notNullValue());
    }

    @Test
    @Step("2. Можно указать один цвет — BLACK")
    public void createOrderWithBlackColor() {
        String requestBody = "{\n" +
                "  \"firstName\": \"Naruto\",\n" +
                "  \"lastName\": \"Uchiha\",\n" +
                "  \"address\": \"Konoha, 142 apt.\",\n" +
                "  \"metroStation\": \"4\",\n" +
                "  \"phone\": \"+7 800 355 35 35\",\n" +
                "  \"rentTime\": 5,\n" +
                "  \"deliveryDate\": \"2020-06-06\",\n" +
                "  \"comment\": \"Saske, come back to Konoha\",\n" +
                "  \"color\": [\"BLACK\"]\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders");

        response.then().statusCode(201);
        response.then().body("track", notNullValue());
    }

    @Test
    @Step("3. Можно не указывать цвет")
    public void createOrderWithoutColor() {
        String requestBody = "{\n" +
                "  \"firstName\": \"Naruto\",\n" +
                "  \"lastName\": \"Uchiha\",\n" +
                "  \"address\": \"Konoha, 142 apt.\",\n" +
                "  \"metroStation\": \"4\",\n" +
                "  \"phone\": \"+7 800 355 35 35\",\n" +
                "  \"rentTime\": 5,\n" +
                "  \"deliveryDate\": \"2020-06-06\",\n" +
                "  \"comment\": \"Saske, come back to Konoha\"\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders");

        response.then().statusCode(201);
        response.then().body("track", notNullValue());
    }

    @Test
    @Step("4. Тело ответа содержит track")
    public void orderResponseContainsTrack() {
        String requestBody = "{\n" +
                "  \"firstName\": \"Naruto\",\n" +
                "  \"lastName\": \"Uchiha\",\n" +
                "  \"address\": \"Konoha, 142 apt.\",\n" +
                "  \"metroStation\": \"4\",\n" +
                "  \"phone\": \"+7 800 355 35 35\",\n" +
                "  \"rentTime\": 5,\n" +
                "  \"deliveryDate\": \"2020-06-06\",\n" +
                "  \"comment\": \"Saske, come back to Konoha\"\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders");

        response.then().statusCode(201);
        response.then().body("track", notNullValue());
    }
}
