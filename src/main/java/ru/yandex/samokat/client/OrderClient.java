package ru.yandex.samokat.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.samokat.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends SamokatBaseClient {
    private final String ORDER_PATH = "/api/v1/orders";

    @Step("Send POST request to /api/v1/orders to create an order")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Send POST request to /api/v1/orders/cancel to cancel an order")
    public ValidatableResponse cancelOrder(int trackNum) {
        String idJson = "{\"name\": "+((Integer)trackNum).toString()+"}";
        return given()
                .spec(getBaseSpec())
                .body(idJson)
                .when()
                .post(ORDER_PATH + "/cancel")
                .then();
    }

    @Step("Send GET request to /api/v1/orders to get the full list of orders")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

}