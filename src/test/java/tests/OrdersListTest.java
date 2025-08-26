package tests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("1. Проверка получения списка заказов")
    public void checkOrdersList() {
        given()
                .auth().oauth2("введи_сюда_свой_токен")
                .param("limit", 30)
                .param("page", 0)
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders", not(empty()))  // Проверка, что список заказов не пуст
                .body("orders.size()", greaterThan(0))  // Проверка, что количество заказов больше 0
                .body("orders[0].id", notNullValue())  // Проверка, что у первого заказа есть id
                .body("orders[0].firstName", notNullValue())  // Проверка, что у первого заказа есть имя клиента
                .body("orders[0].address", notNullValue());  // Проверка, что у первого заказа есть адрес
    }

    @Test
    @Step("2. Проверка на запрос с несуществующим courierId")
    public void checkNonExistentCourierId() {
        given()
                .auth().oauth2("введи_сюда_свой_токен")
                .param("courierId", 9999)  // Несуществующий courierId
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(404)
                .body("message", equalTo("Курьер с идентификатором 9999 не найден"));
    }
}
