package ru.netology;


import com.codeborne.selenide.Condition;

import com.epam.reportportal.junit5.ReportPortalExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;



@ExtendWith(ReportPortalExtension.class)
public class CallBackTests {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(CallBackTests.class);

    @BeforeEach
    public void setUP() {
        open("http://localhost:9999");
    }


    @Test
    public void shouldWorkHappyPath() {
        var validUser = DataGenerator.Registration.generateUser();

        $("[data-test-id=\"city\"] .input__control").setValue(validUser.getNativeCity());
        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(validUser.getOldDate());
        $("[data-test-id=\"name\"] .input__control").setValue(validUser.getFullName());
        $("[data-test-id=\"phone\"] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=\"agreement\"] .checkbox__box").click();
        $(".form-field .button__content").click();
        $("[data-test-id=\"success-notification\"] .notification__title").should(visible, Duration.ofSeconds(5));
        $("[data-test-id=\"success-notification\"] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + validUser.getOldDate()), Duration.ofSeconds(5));

        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(validUser.getNewDate());
        $(".form-field .button__content").click();
        $("[data-test-id=\"replan-notification\"] button").click();
        $("[data-test-id=\"success-notification\"] .notification__title").should(visible, Duration.ofSeconds(5));
        $("[data-test-id=\"success-notification\"] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + validUser.getNewDate()), Duration.ofSeconds(5));
        LOGGER.info("Logger test message");
    }


}
