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

public class CreateCourierTest {
    private Courier courier;
    private CourierActions courierClient;
    private String id;

    @Before
    public void setUp() {
        courier = CourierMaker.random();
        courierClient = new CourierActions();
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка успешного создания курьера")
    public void createCourier() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        id = loginResponse.extract().path("id").toString();
        response.assertThat().statusCode(201).body("ok", is(true));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    // @Description("При отсутствии логина курьера в запросе возвращаем ошибку 'Недостаточно данных для создания учетной записи' с кодом 400")
    public void createCourierWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("При отсутствии пароля курьера в запросе возвращаем ошибку 'Недостаточно данных для создания учетной записи' с кодом 400")
    public void createCourierWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с одинаковым логином")
    @Description("Если логин, переданный в запросе не уникален, то возвращаем ошибку 'Этот логин уже используется. Попробуйте другой' с кодом 409")
    public void createCourierDouble() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(409).and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void cleanUp() {
        if (id != null) {
            courierClient.delete(id);
        }
    }
}
