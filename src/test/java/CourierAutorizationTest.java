import courier.Courier;
import courier.CourierActions;
import courier.CourierMaker;
import courier.Credentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierAutorizationTest {
    private Courier courier;
    private Credentials credentials;
    private CourierActions courierClient;
    private String id;

    @Before
    public void setUp() {
        courier = CourierMaker.random();
        courierClient = new CourierActions();
    }

    @Test
    @DisplayName("Авторизации курьера")
    @Description("Проверка успешной авторизации курьера")
    public void autorizationCourier() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        id = loginResponse.extract().path("id").toString();
        response.assertThat().statusCode(201).body("ok", is(true));
    }

    @Test
    @DisplayName("Тестирование авторизации курьера без логина")
    @Description("При отсутствии логина курьера в запросе на авторизацию возвращаем ошибку 'Недостаточно данных для входа' с кодом 400")
    public void autorizationCourierWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.login(Credentials.from(courier));
        response.statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тестирование авторизации курьера без логина")
    @Description("При отсутствии пароля курьера в запросе на авторизацию возвращаем ошибку 'Недостаточно данных для входа' с кодом 400")
    public void autorizationCourierWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.login(Credentials.from(courier));
        response.statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void cleanUp() {
        if (id != null) {
            courierClient.delete(id);
        }
    }
}
