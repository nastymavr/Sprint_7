package tests;

import api.OrderApi;
import client.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.not;
import static org.apache.http.HttpStatus.SC_CREATED;

public class OrderCreateTest extends BaseTest {

    @Test
    @Step("1. Создание заказа с двумя цветами: BLACK и GREY")
    public void createOrderWithBlackAndGreyColors() {
        Order order = new Order(
                "Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                "+7 800 355 35 35", 5, "2020-06-06",
                "Saske, come back to Konoha",
                java.util.List.of("BLACK", "GREY")
        );

        Response response = OrderApi.createOrder(order);
        response.then().statusCode(SC_CREATED);
    }

    @Test
    @Step("5. Проверка, что тело ответа содержит поле track")
    public void orderResponseContainsTrack() {
        Order order = new Order(
                "Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                "+7 800 355 35 35", 5, "2020-06-06",
                "Saske, come back to Konoha",
                null
        );

        Response response = OrderApi.createOrder(order);
        response.then().statusCode(SC_CREATED);
        response.then().body("track", notNullValue());
    }
}
