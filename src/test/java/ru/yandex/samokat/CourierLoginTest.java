package ru.yandex.samokat;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierCredentials;
import ru.yandex.samokat.model.CourierRandomizer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    private Courier courier;
    private CourierClient courierClient;
    private CourierRandomizer courierRandomizer = new CourierRandomizer();
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = courierRandomizer.getRandomFields(true, true, true);
        courierClient.createCourier(courier);
    }
    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Courier can login with valid credentials")
    @Description("Check 1) Status code 200 2) Id is not 0")
    public void courierCanLoginWithValidCredentialsTest() {

        ValidatableResponse response = courierClient.loginCourier(CourierCredentials.form(courier));

        int statusCode = response.extract().statusCode();
        courierId = response.extract().path("id");

        response.assertThat().statusCode(200); //проверяем, что статус код ответа 200
        assertThat("Courier ID is incorrect", courierId, is(not(0))); //проверяем, что id ненулевой
    }

    @Test
    @DisplayName("Incorrect login returns an error")
    @Description("Check 1) Status code 400 2) Message is \"Учетная запись не найдена\"")
    public void incorrectLoginReturnErrorTest(){
        String loginTest ="321"+courier.getLogin()+"123";

        ValidatableResponse validatableResponse = courierClient.loginCourier(new CourierCredentials(loginTest, courier.getPassword()));

        validatableResponse.assertThat().statusCode(404);
        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Incorrect password returns an error")
    @Description("Check 1) Status code 400 2) Message is \"Учетная запись не найдена\"")
    public void incorrectPasswordReturnErrorTest(){
        String passwordTest = "321"+courier.getPassword()+"123";

        ValidatableResponse validatableResponse = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), passwordTest));

        validatableResponse.assertThat().statusCode(404);
        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login is impossible if courier wasnt created")
    @Description("Check 1) Status code 400 2) Message is \"Учетная запись не найдена\"")
    public void loginImpossibleCourierNotExistsTest(){
        String loginUnreal = "unreallogin";
        String passwordUnreal = "unrealpassword";

        ValidatableResponse validatableResponse = courierClient.loginCourier(new CourierCredentials(loginUnreal, passwordUnreal));

        validatableResponse.assertThat().statusCode(404);
        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }


}
