package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.model.OrderRandomizer;

import java.util.List;

public class OrderListTest {
    private OrderClient orderClient = new OrderClient();
    private OrderRandomizer orderRandomizer = new OrderRandomizer();
    private int truckNumber;
    private ValidatableResponse responseCreation;

    @Before
    public void setUp() {
        responseCreation = orderClient.createOrder(orderRandomizer.getRandomOrderData());
        truckNumber = responseCreation.extract().path("track");
    }

    @After
    public void tearDown() {
        orderClient.cancelOrder(truckNumber);
    }

    @Test
    @DisplayName("Check that list of Orders is returned")
    @Description("Check 1)Status code is 200 2) List is not empty")
    public void notEmptyListIsReturnedTest() {
        ValidatableResponse response = orderClient.getOrderList();
        List<Object> ordersList = response.extract().jsonPath().getList("orders");
        List<Object> listOfTrackNumbers = response.extract().jsonPath().getJsonObject("orders.track");

        response.assertThat().statusCode(200);
        Assert.assertFalse(ordersList.isEmpty());
        Assert.assertTrue(listOfTrackNumbers.contains(truckNumber));

    }
}
