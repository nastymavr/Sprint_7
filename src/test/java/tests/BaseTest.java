package tests;

import api.CourierApi;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    protected Integer courierId; // Для удаления курьера после теста
    protected static RequestSpecification requestSpec;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        requestSpec = given().header("Content-type", "application/json");
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            CourierApi.deleteCourier(courierId);
            courierId = null;
        }
    }
}
