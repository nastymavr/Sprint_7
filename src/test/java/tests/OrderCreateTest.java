package tests;

import api.OrderApi;
import client.Order;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {

    private final List<String> colors;

    public OrderCreateTest(List<String> colors) {
        this.colors = colors;
    }

    // Параметры для теста
    @Parameterized.Parameters(name = "Цвета заказа: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { Arrays.asList("BLACK", "GREY") },
                { Arrays.asList("BLACK") },
                { Arrays.asList("GREY") },
                { null } // без цвета
        });
    }

    @Test
    public void createOrderWithColors() {
        Order order = new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                colors
        );

        Response response = OrderApi.createOrder(order);
        response.then().statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}
