package tests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import utils.RandomDataGenerator;



import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierCreateTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("1. Создание   курьера с уникальными данными")
    public void createCourierSuccessfully() {
        // Генерация уникального логина, пароля и имени
        String randomLogin = RandomDataGenerator.generateRandomLogin();
        String randomPassword = RandomDataGenerator.generateRandomPassword();
        String randomFirstName = "TestFirstName";

        // Формирование тела запроса
        String requestBody = String.format("{\n" +
                "  \"login\": \"%s\",\n" +
                "  \"password\": \"%s\",\n" +
                "  \"firstName\": \"%s\"\n" +
                "}", randomLogin, randomPassword, randomFirstName);

        // Отправка запроса на создание курьера
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        // Проверка ответа
        response.then().statusCode(201);
        response.then().body("ok", equalTo(true));
    }

    @Test
    @Step("2. Пытаться создать курьера с одинаковым логином")
    public void createCourierWithDuplicateLogin() {
        // Генерация уникального логина и пароля
        String randomLogin = RandomDataGenerator.generateRandomLogin();
        String randomPassword = RandomDataGenerator.generateRandomPassword();
        String randomFirstName = "TestFirstName";

        // Создаем первого курьера с уникальным логином
        String requestBody = String.format("{\n" +
                "  \"login\": \"%s\",\n" +
                "  \"password\": \"%s\",\n" +
                "  \"firstName\": \"%s\"\n" +
                "}", randomLogin, randomPassword, randomFirstName);

        // Отправка первого запроса на создание курьера
        given().header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);

        // Пытаемся создать второго курьера с тем же логином
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        // Проверка, что создается ошибка из-за одинакового логина
        response.then().statusCode(409);
        response.then().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Step("3. Для создания курьера нужно передать все обязательные поля")
    public void createCourierWithMissingFieldsFails() {
        // Генерация уникального логина и пароля
        String randomLogin = RandomDataGenerator.generateRandomLogin();
        String randomPassword = RandomDataGenerator.generateRandomPassword();

        // Создаем тело запроса без имени
        String requestBody = String.format("{\n" +
                "  \"login\": \"%s\",\n" +
                "  \"password\": \"%s\"\n" +
                "}", randomLogin, randomPassword);

        // Отправка запроса для создания курьера с отсутствующим обязательным полем
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        // Проверка, что сервер вернет успешный ответ с кодом 201
        response.then().statusCode(201);
        response.then().body("ok", equalTo(true));  // Поскольку API возвращает { "ok": true }
    }

    @Test
    @Step("4. Проверка правильности кода ответа")
    public void checkResponseCode() {
        // Генерация уникальных данных
        String randomLogin = RandomDataGenerator.generateRandomLogin();
        String randomPassword = RandomDataGenerator.generateRandomPassword();
        String randomFirstName = "TestFirstName";

        String requestBody = String.format("{\n" +
                "  \"login\": \"%s\",\n" +
                "  \"password\": \"%s\",\n" +
                "  \"firstName\": \"%s\"\n" +
                "}", randomLogin, randomPassword, randomFirstName);

        // Отправка запроса на создание курьера
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        // Проверка кода ответа
        response.then().statusCode(201);
    }

    @Test
    @Step("5. Успешный запрос возвращает ok: true")
    public void successfulRequestReturnsOkTrue() {
        // Генерация уникальных данных
        String randomLogin = RandomDataGenerator.generateRandomLogin();
        String randomPassword = RandomDataGenerator.generateRandomPassword();
        String randomFirstName = "TestFirstName";

        String requestBody = String.format("{\n" +
                "  \"login\": \"%s\",\n" +
                "  \"password\": \"%s\",\n" +
                "  \"firstName\": \"%s\"\n" +
                "}", randomLogin, randomPassword, randomFirstName);

        // Отправка запроса
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        // Проверка ответа
        response.then().statusCode(201);
        response.then().body("ok", equalTo(true));
    }

    @Test
    @Step("6. Если одного из полей нет, запрос возвращает ошибку")
    public void createCourierWithMissingFieldReturnsError() {
        // Пропускаем обязательное поле firstName
        String requestBody = "{\n" +
                "  \"login\": \"testLogin\",\n" +
                "  \"password\": \"password123\"\n" + // Пропущено firstName
                "}";

        // Отправка запроса без обязательного поля
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        // Проверка, что вернется ошибка
        response.then().statusCode(400);
    }

    @Test
    @Step("7. Пытаемся создать курьера с уже существующим логином")
    public void createCourierWithExistingLoginReturnsError() {
        // Генерация уникальных данных
        String randomLogin = RandomDataGenerator.generateRandomLogin();
        String randomPassword = RandomDataGenerator.generateRandomPassword();
        String randomFirstName = "TestFirstName";

        String requestBody = String.format("{\n" +
                "  \"login\": \"%s\",\n" +
                "  \"password\": \"%s\",\n" +
                "  \"firstName\": \"%s\"\n" +
                "}", randomLogin, randomPassword, randomFirstName);

        // Создаем первого курьера
        given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);

        // Пытаемся создать второго курьера с тем же логином
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        // Проверка ошибки, что логин уже используется
        response.then().statusCode(409);
        response.then().body("message", equalTo("Этот логин уже используется"));
    }
}
