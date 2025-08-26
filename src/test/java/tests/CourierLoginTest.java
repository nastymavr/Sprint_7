package tests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import utils.RandomDataGenerator;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("1. Курьер успешно авторизуется с правильными данными")
    public void loginCourierSuccessfully() {
        String randomLogin = RandomDataGenerator.generateRandomLogin();
        String randomPassword = RandomDataGenerator.generateRandomPassword();
        String randomFirstName = "TestFirstName";

        String requestBody = String.format("{\n" +
                "  \"login\": \"%s\",\n" +
                "  \"password\": \"%s\",\n" +
                "  \"firstName\": \"%s\"\n" +
                "}", randomLogin, randomPassword, randomFirstName);

        // Создаем курьера
        given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        // Логин
        String loginRequestBody = "{\n" +
                "  \"login\": \"" + randomLogin + "\",\n" +
                "  \"password\": \"" + randomPassword + "\"\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login");

        response.then().statusCode(200);
        response.then().body("id", notNullValue());
    }

    @Test
    @Step("2. Логин курьера с неверными данными возвращает ошибку")
    public void loginCourierWithWrongLogin() {
        String wrongLogin = "wrongLogin";
        String wrongPassword = "wrongPassword";

        String loginRequestBody = "{\n" +
                "  \"login\": \"" + wrongLogin + "\",\n" +
                "  \"password\": \"" + wrongPassword + "\"\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login");

        response.then().statusCode(404);
        response.then().body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @Step("3. Логин курьера без пароля возвращает ошибку")
    public void loginCourierWithoutPassword() {
        // 1. Генерация данных курьера
        String randomLogin = RandomDataGenerator.generateRandomLogin();
        String randomPassword = RandomDataGenerator.generateRandomPassword();
        String randomFirstName = "TestFirstName";

        // 2. Создание курьера
        String createRequestBody = String.format("{\"login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}",
                randomLogin, randomPassword, randomFirstName);

        given()
                .header("Content-type", "application/json")
                .body(createRequestBody)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201); // Курьер должен успешно создаться

        // 3. Попытка логина без пароля
        String loginRequestBody = String.format("{\"login\": \"%s\"}", randomLogin);

        Response response = given()
                .header("Content-type", "application/json")
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login");

        // 4. Логирование запроса и ответа
        response.then().log().all();

        // 5. Проверка: должен быть статус 400 и соответствующее сообщение
        response.then().statusCode(400);
        response.then().body("message", equalTo("Недостаточно данных для входа"));
    }
}
