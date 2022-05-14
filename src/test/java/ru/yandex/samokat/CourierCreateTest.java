package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierCredentials;
import ru.yandex.samokat.model.CourierRandomizer;

public class CourierCreateTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;
    private CourierRandomizer randomCourier = new CourierRandomizer();

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = randomCourier.getRandomFields(true, true, true);
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Courier can be created with valid data")
    @Description("Check 1) Status code 201 2) Message has ok:true")
    public void createCourierWithValidDataTest() {

        ValidatableResponse response = courierClient.createCourier(courier);
        boolean isCourierCreated = response.extract().path("ok");
        courierId = courierClient.loginCourier(CourierCredentials.form(courier)).extract().path("id");

        response.assertThat().statusCode(201); //проверяем, что статус код ответа 201
        assertTrue("Courier is not created", isCourierCreated); // проверяем, что тело ответа содержит ок
    }

    @Test
    @DisplayName("Two identical couriers cant be created")
    @Description("Check 1) Status code 409 2) Message has \"Этот логин уже используется\"")
    public void cantCreateTwoIdenticalCouriersTest(){
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.form(courier)).extract().path("id");
        ValidatableResponse response = courierClient.createCourier(courier);

        response.assertThat().statusCode(409);
        response.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Two couriers with the same login cant be created")
    @Description("Check 1) Status code 409 2) Message has \"Этот логин уже используется\"")
    public void cantCreateTwoCouriersWithSameLoginTest(){
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.form(courier)).extract().path("id");
        courier.setPassword(courier.getPassword()+"123");
        ValidatableResponse response = courierClient.createCourier(courier);

        response.assertThat().statusCode(409);
        response.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

}
