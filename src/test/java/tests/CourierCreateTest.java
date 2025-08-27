package tests;

import api.CourierApi;
import client.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import utils.RandomDataGenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class CourierCreateTest extends BaseTest {

    @Test
    @Step("1. Создание курьера с уникальными данными")
    public void createCourierSuccessfully() {
        Courier courier = new Courier(
                RandomDataGenerator.generateRandomLogin(),
                RandomDataGenerator.generateRandomPassword(),
                "TestFirstName"
        );

        Response response = CourierApi.createCourier(courier);
        response.then().statusCode(SC_CREATED);

        response.then().body("ok", equalTo(true));

        courierId = CourierApi.loginCourier(courier.getLogin(), courier.getPassword())
                .then().statusCode(SC_OK)
                .extract().path("id");
    }

    @Test
    @Step("2. Попытка создать курьера с одинаковым логином")
    public void createCourierWithDuplicateLogin() {
        String login = RandomDataGenerator.generateRandomLogin();
        Courier courier = new Courier(login, "password123", "TestFirstName");

        CourierApi.createCourier(courier).then().statusCode(SC_CREATED);

        Response response = CourierApi.createCourier(courier);
        response.then().statusCode(SC_CONFLICT);
        response.then().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Step("3. Проверка правильности кода ответа при создании курьера")
    public void checkResponseCode() {
        Courier courier = new Courier(
                RandomDataGenerator.generateRandomLogin(),
                RandomDataGenerator.generateRandomPassword(),
                "TestFirstName"
        );

        Response response = CourierApi.createCourier(courier);
        response.then().statusCode(SC_CREATED);

        courierId = CourierApi.loginCourier(courier.getLogin(), courier.getPassword())
                .then().statusCode(SC_OK)
                .extract().path("id");
    }

    @Test
    @Step("4. Успешный запрос возвращает ok: true")
    public void successfulRequestReturnsOkTrue() {
        Courier courier = new Courier(
                RandomDataGenerator.generateRandomLogin(),
                RandomDataGenerator.generateRandomPassword(),
                "TestFirstName"
        );

        Response response = CourierApi.createCourier(courier);
        response.then().statusCode(SC_CREATED);
        response.then().body("ok", equalTo(true));

        courierId = CourierApi.loginCourier(courier.getLogin(), courier.getPassword())
                .then().statusCode(SC_OK)
                .extract().path("id");
    }

    @Step("5. Создание курьера без необязательного поля firstName")
    public void createCourierWithoutOptionalFirstName() {
        Courier courier = new Courier("testLogin", "password123", null);

        Response response = CourierApi.createCourier(courier);
        response.then().statusCode(SC_CREATED)
                .body("ok", equalTo(true));

        // Логинимся для удаления курьера в @After
        courierId = CourierApi.loginCourier(courier.getLogin(), courier.getPassword())
                .then().statusCode(SC_OK)
                .extract().path("id");
    }

    @Test
    @Step("6. Пытаемся создать курьера с уже существующим логином")
    public void createCourierWithExistingLoginReturnsError() {
        String login = RandomDataGenerator.generateRandomLogin();
        Courier courier = new Courier(login, "password123", "TestFirstName");

        CourierApi.createCourier(courier).then().statusCode(SC_CREATED);

        Response response = CourierApi.createCourier(courier);
        response.then().statusCode(SC_CONFLICT);
        response.then().body("message", equalTo("Этот логин уже используется"));

        courierId = CourierApi.loginCourier(courier.getLogin(), courier.getPassword())
                .then().statusCode(SC_OK)
                .extract().path("id");
    }
}
