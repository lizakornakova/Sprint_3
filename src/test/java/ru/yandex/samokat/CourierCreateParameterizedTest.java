package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierRandomizer;

@RunWith(Parameterized.class)
public class CourierCreateParameterizedTest {
    private final Courier courier;
    private final int statusCodeExpected;
    private final String messageExpected;

    public CourierCreateParameterizedTest(Courier courier, int statusCodeExpected, String messageExpected){
        this.courier = courier;
        this.statusCodeExpected = statusCodeExpected;
        this.messageExpected = messageExpected;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][] {
                {new CourierRandomizer().getRandomFields(false,true,true), 400, "Недостаточно данных для создания учетной записи"},
                {new CourierRandomizer().getRandomFields(true,false,true), 400, "Недостаточно данных для создания учетной записи"},
                {new CourierRandomizer().getRandomFields(true,true,false), 400, "Недостаточно данных для создания учетной записи"},
        };
    }

    @Test
    @DisplayName("Courier cant be created without one of the parameters: login, password or firstName")
    @Description("Check 1) Status code 400 2) Message has \"Недостаточно данных для создания учетной записи\"")
    public void createCourierWithInvalidDataTest() {
        ValidatableResponse response = new CourierClient().createCourier(courier);

        int statusCodeActual = response.extract().statusCode();
        String messageActual = response.extract().path("message");

        Assert.assertEquals(statusCodeExpected, statusCodeActual);
        Assert.assertEquals(messageExpected, messageActual);

    }

}
