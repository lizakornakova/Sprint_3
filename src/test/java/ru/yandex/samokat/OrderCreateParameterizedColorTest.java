package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.model.Order;
import ru.yandex.samokat.model.OrderRandomizer;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedColorTest {
    private final Order order;
    private final int statusCodeExpected;
    private int trackNumber;

    public OrderCreateParameterizedColorTest(Order order, int statusCodeExpected){
        this.order = order;
        this.statusCodeExpected = statusCodeExpected;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][] {
                {new OrderRandomizer().getOrderWithColor(new String[]{"BLACK","GREY"}), 201},
                {new OrderRandomizer().getOrderWithoutColor(), 201},
                {new OrderRandomizer().getOrderWithColor(new String[]{"BLACK"}), 201},
                {new OrderRandomizer().getOrderWithColor(new String[]{"GREY"}), 201}
        };
    }


    @After
    public void tearDown() {
        new OrderClient().cancelOrder(trackNumber);
    }

    @Test
    @DisplayName("Order can be created with 1)two colors black and grey 2) no color 3) either black 4) either grey")
    @Description("Check 1) Status code 201 2) Track number is nor 0")
    public void createOrderWithDifferentColorsTest() {

        ValidatableResponse response = new OrderClient().createOrder(order);
        int statusCodeActual = response.extract().statusCode();
        trackNumber = response.extract().path("track");

        Assert.assertEquals(statusCodeActual, statusCodeExpected);
        assertThat("Track is null", trackNumber, is(not(0)));

    }
}
