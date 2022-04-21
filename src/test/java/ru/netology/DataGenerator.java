package ru.netology;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    static Faker ghostOne = new Faker(new Locale("RU"));

    private DataGenerator() {
    }

    public static String generateOldDate() {
        String oldDate = LocalDate.now().plusDays(ghostOne.random().nextInt(5, 8)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return oldDate;
    }

    public static String generateNewDate() {
        String newDate = LocalDate.now().plusDays(ghostOne.random().nextInt(9, 13)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return newDate;
    }

    public static String generateCity() {
        TrueCity addCity = new TrueCity();
        return addCity.ValidCity();
    }

    public static String generateName() {
        String firstName = ghostOne.name().firstName().replaceAll("ё", "е");
        String lastName = ghostOne.name().lastName().replaceAll("ё", "е");
        String fullName = firstName + " " + lastName;
        return fullName;
    }

    public static String generatePhone() {
        String phone = "+" + ghostOne.phoneNumber().phoneNumber().toString().replaceAll("\\D", "");
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static FellowOne generateUser() {
            FellowOne user = new FellowOne(
                    generateName(),
                    generatePhone(),
                    generateOldDate(),
                    generateNewDate(),
                    generateCity());
            return user;
        }
    }

    @Value
    public static class FellowOne {
        private String fullName;
        private String phone;
        private String oldDate;
        private String newDate;
        private String nativeCity;
    }
}
