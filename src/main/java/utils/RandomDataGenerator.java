package utils;

import java.util.Random;

public class RandomDataGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    // Генерация случайного логина
    public static String generateRandomLogin() {
        return generateRandomString(10);  // Длина логина 10 символов
    }

    // Генерация случайного пароля
    public static String generateRandomPassword() {
        return generateRandomString(12);  // Длина пароля 12 символов
    }

    // Генерация случайной строки
    private static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }
}
