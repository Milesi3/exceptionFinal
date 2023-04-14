import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Пожалуйста, введите данные пользователя в следующем формате: фамилия имя отчество дата рождения номер телефона пол");
        String[] userData = scanner.nextLine().split("\\s+");

        // validate the entered data by quantity
        if (userData.length != 6) {
            System.out.println("Ошибка: неверное количество входных параметров");
            System.out.println("Ожидаемый формат: фамилия имя отчество дата рождения номер телефона пол");
            return;
        }

        String lastName = userData[0];
        String firstName = userData[1];
        String middleName = userData[2];
        String dateOfBirth = userData[3];
        String phoneNumber = userData[4];
        String gender = userData[5];

        // validate date of birth format
        try {
            LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            LocalDate now = LocalDate.now();
            long years = dob.until(now, ChronoUnit.YEARS);
            if (years < 0 || years > 150) {
                throw new IllegalArgumentException("Неверная дата рождения: " + dateOfBirth);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: неверный формат даты рождения");
            System.out.println("Ожидаемый формат: дд.ММ.гггг");
            return;
        }

        // validate phone number format
        if (!phoneNumber.matches("\\d+")) {
            System.out.println("Ошибка: неверный формат телефонного номера");
            System.out.println("Ожидаемый формат: целое число без знака без форматирования");
            return;
        }

        // validate gender
        if (!gender.matches("[мж]")) {
            System.out.println("Ошибка: неверный гендерный формат");
            System.out.println("Ожидаемый формат: латинский символ м или ж");
            return;
        }

        // construct the user data string
        String userDataString = lastName + firstName + middleName + dateOfBirth + " " + phoneNumber + gender;

        // write the user data to a file
        String fileName = lastName.toLowerCase() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(userDataString);
            writer.newLine();
            System.out.println("Пользовательские данные, сохраненные в файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + fileName);
            e.printStackTrace();
        }
    }
}