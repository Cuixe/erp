package org.ceneval.examen.erp.flow;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.aspectj.weaver.ast.Or;
import org.ceneval.examen.erp.ErpApplication;
import org.ceneval.examen.erp.entity.Order;
import org.ceneval.examen.erp.entity.OrderDetail;
import org.ceneval.examen.erp.utils.JsonMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ErpApplication.class }, webEnvironment
        = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderIntegrationTest {

    private static final String API_ROOT = "http://localhost:8080/api/order";

    private Order getOrderById(int id) {
        Response response = RestAssured.get(API_ROOT + "/" + id);
        return JsonMapper.mapOrder(response);
    }

    @Test
    public void addAndRemoveClient() {
        Order order = new Order();
        order.setClientId(1);
        order.setOrderDate(new Date(Instant.EPOCH.toEpochMilli()));
        order.setOrderStatus(1);
        order.setOrderAmount(100);
        order.addOrderDetail(new OrderDetail(1, 5));
        order.addOrderDetail(new OrderDetail(1, 2));

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(order)
                .post(API_ROOT);

        order = JsonMapper.mapOrder(response);
        Assert.assertTrue(order.getClientId() > 0);

        response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(order)
                .delete(API_ROOT + "/" + order.getId());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(API_ROOT + "/" + order.getId());
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void getAllOrders() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        List<Order> orders = JsonMapper.mapListOrders(response);

        Assert.assertTrue(orders.size() > 0);
        Assert.assertEquals(2, orders.get(0).getOrderDetails().size());
    }

    @Test
    public void getSpecificOrder() {
        Order order = getOrderById(8);
        Assert.assertEquals(2, order.getOrderDetails().size());
    }

    @Test
    public void updateOrder() {
        Order order = getOrderById(8);
        int status = order.getOrderStatus();
        order.setOrderStatus(2);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(order)
                .put(API_ROOT);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Order newOrder = getOrderById(8);
        Assert.assertEquals(2, newOrder.getOrderStatus());

        newOrder.setOrderStatus(status);
        response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(order)
                .put(API_ROOT);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }



    @Test
    public void updateDetail() {
        Order order = getOrderById(8);
        OrderDetail detail = order.getOrderDetails().get(0);
        int idDetail = detail.getId();
        detail.setProductsAmount(50);

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(order)
                .put(API_ROOT);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        response = RestAssured.get(API_ROOT + "/8");

        Order newOrder = JsonMapper.mapOrder(response);
        newOrder.getOrderDetails().forEach(orderDetail -> {
            if (orderDetail.getId() == idDetail) {
                Assert.assertEquals(50, orderDetail.getProductsAmount());
            }
        });
    }
}
