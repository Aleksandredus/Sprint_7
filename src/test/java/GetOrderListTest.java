import io.qameta.allure.Description;
import order.OrderActions;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest {
    private OrderActions orderActions;

    @Before
    public void setUp() {
        orderActions = new OrderActions();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка успешного выполнения запроса и получение заполненного списка заказов")
    public void checklistOrderTest() {
        ValidatableResponse response = orderActions.orderList();
        response.statusCode(200).and().body("orders", notNullValue());
    }
}
