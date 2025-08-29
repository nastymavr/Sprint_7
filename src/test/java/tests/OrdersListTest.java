package tests;

import api.OrderApi;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrdersListTest extends BaseTest {

    @Test
    public void checkOrdersList() {
        given()
                .auth().oauth2("введи_сюда_свой_токен")
                .param("limit", 30)
                .param("page", 0)
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders", not(empty()))
                .body("orders.size()", greaterThan(0))
                .body("orders[0].id", notNullValue())
                .body("orders[0].firstName", anyOf(nullValue(), notNullValue()))
                .body("orders[0].address", anyOf(nullValue(), notNullValue()));
    }

    @Test
    public void checkNonExistentCourierId() {
        Response response = OrderApi.getOrdersList(9999);

        response.then().statusCode(404)
                .body("message", equalTo("Курьер с идентификатором 9999 не найден"));
    }
}
