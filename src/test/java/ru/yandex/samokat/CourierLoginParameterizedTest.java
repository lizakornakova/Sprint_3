package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.model.CourierCredentials;
import ru.yandex.samokat.model.CourierRandomizer;

@RunWith(Parameterized.class)
public class CourierLoginParameterizedTest {
    private final CourierCredentials courierCredentials;
    private final int statusCodeExpected;
    private final String messageExpected;

    public CourierLoginParameterizedTest(CourierCredentials courierCredentials, int statusCodeExpected, String messageExpected) {
        this.courierCredentials = courierCredentials;
        this.statusCodeExpected = statusCodeExpected;
        this.messageExpected = messageExpected;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][]{
                {CourierCredentials.form(new CourierRandomizer().getRandomFields(true, false, true)), 400, "Недостаточно данных для входа"},
                {CourierCredentials.form(new CourierRandomizer().getRandomFields(false, true, true)), 400, "Недостаточно данных для входа"},
        };
    }
    @Test
    @DisplayName("Courier cant login without 1) login 2) password")
    @Description("Check 1) Status code 400 2) Message has \"Недостаточно данных для входа\"")
    public void loginCourierWithInvalidDataTest() {
        ValidatableResponse response = new CourierClient().loginCourier(courierCredentials);

        int statusCodeActual = response.extract().statusCode();
        String messageActual = response.extract().path("message");

        Assert.assertEquals(statusCodeExpected, statusCodeActual);
        Assert.assertEquals(messageExpected, messageActual);

    }
}
