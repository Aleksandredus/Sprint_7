package courier;

import specification.RequestSpecifications;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CourierActions extends RequestSpecifications {
    protected final CourierMaker generator = new CourierMaker();
    private static final String CREATE_OR_DELETE_COURIER = "/api/v1/courier";
    private static final String LOGIN_COURIER = "api/v1/courier/login";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .when()
                .body(courier)
                .post(CREATE_OR_DELETE_COURIER)
                .then().log().all();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec((RequestSpecification) getSpec())
                .when()
                .body(credentials)
                .post(LOGIN_COURIER)
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(String id) {
        return given()
                .spec((RequestSpecification) getSpec())
                .when()
                .delete(CREATE_OR_DELETE_COURIER + id)
                .then();
    }
}
