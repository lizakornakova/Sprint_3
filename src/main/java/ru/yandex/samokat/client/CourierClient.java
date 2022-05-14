package ru.yandex.samokat.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierCredentials;

import static io.restassured.RestAssured.*;

public class CourierClient extends SamokatBaseClient {
    private final String COURIER_PATH = "/api/v1/courier";

    @Step("Send POST request to /api/v1/courier to create a courier")
    public ValidatableResponse createCourier(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Send POST request to /api/v1/courier/login to login a courier")
    public ValidatableResponse loginCourier(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then();
    }

    @Step("Send POST request to /api/v1/courier/:id to delete a courier")
    public ValidatableResponse deleteCourier(int courierId){
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }
}
