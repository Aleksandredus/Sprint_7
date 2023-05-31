package order;

import specification.RequestSpecifications;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderActions extends RequestSpecifications {
    static private String orders = "/api/v1/orders";
    static private String cancelOrder = "/api/v1/orders/cancel";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given().log().all()
                .spec(getSpec())
                .body(order)
                .when()
                .post(orders)
                .then();
    }

    @Step("Удаление заказа")
    public ValidatableResponse delete(int track) {
        return given().log().all()
                .spec(getSpec())
                .body(track)
                .when()
                .put(cancelOrder)
                .then();
    }

    @Step("Список заказов")
    public ValidatableResponse orderList() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .get(orders)
                .then()
                .assertThat();
    }
}

