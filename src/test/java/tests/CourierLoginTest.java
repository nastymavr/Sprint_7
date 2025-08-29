package tests;

import api.CourierApi;
import client.Courier;
import client.CourierLogin;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import utils.RandomDataGenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class CourierLoginTest extends BaseTest {

    private Courier courier;

    @Before
    public void createTestCourier() {
        courier = new Courier(
                RandomDataGenerator.generateRandomLogin(),
                RandomDataGenerator.generateRandomPassword(),
                "TestFirstName"
        );

        CourierApi.createCourier(courier).then().statusCode(SC_CREATED);
        courierId = CourierApi.loginCourier(courier.getLogin(), courier.getPassword())
                .then().statusCode(SC_OK)
                .extract().path("id");
    }

    @Test
    public void loginCourierSuccessfully() {
        Response response = CourierApi.loginCourier(new CourierLogin(courier.getLogin(), courier.getPassword()));
        response.then().statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    public void loginCourierWithWrongLogin() {
        Response response = CourierApi.loginCourier(new CourierLogin("wrongLogin", courier.getPassword()));
        response.then().statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWithWrongPassword() {
        Response response = CourierApi.loginCourier(new CourierLogin(courier.getLogin(), "wrongPassword"));
        response.then().statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWithoutPassword() {
        Response response = CourierApi.loginCourier(new CourierLogin(courier.getLogin(), null));
        response.then().statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
