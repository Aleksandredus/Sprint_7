package specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public class RequestSpecifications {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String CREATE_OR_DELETE_COURIER = "/api/v1/courier";
    private static final String LOGIN_COURIER = "api/v1/courier/login";

    protected io.restassured.specification.RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }
}
