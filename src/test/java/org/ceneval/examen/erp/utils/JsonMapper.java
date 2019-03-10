package org.ceneval.examen.erp.utils;

import io.restassured.response.Response;
import org.ceneval.examen.erp.entity.Client;
import org.ceneval.examen.erp.entity.Order;
import org.ceneval.examen.erp.entity.OrderDetail;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JsonMapper {

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

    private static Date parseDate (String stringDate)  {
        try {
            java.util.Date date1 = sdf1.parse(stringDate);
            return new Date(date1.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Client> mapListClients(Response response) {
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        List<Map> data = response.jsonPath().get();
        List<Client> clients = new ArrayList<>();
        data.forEach(row -> {
            clients.add(getClient(row));
        });
        return clients;
    }

    public static Client mapClient(Response response) {
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Map data = response.jsonPath().get();
        Client client = getClient(data);
        return client;
    }

    private static Client getClient(Map data) {
        Client client = new Client();
        client.setName((String) data.get("name"));
        client.setAddress((String) data.get("address"));
        client.setId((int) data.get("id"));
        return client;
    }

    public static List<Order> mapListOrders(Response response) {
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        List<Map> data = response.jsonPath().get();
        List<Order> orders = new ArrayList<>();
        data.forEach(row -> orders.add(getOrder(row)));
        return orders;
    }

    public static Order mapOrder(Response response) {
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Map data = response.jsonPath().get();
        Order order = getOrder(data);
        return order;
    }

    private static Order getOrder(Map data) {
        Order order = new Order();
        order.setId((Integer) data.get("id"));
        float amount = (Float) data.get("orderAmount");
        order.setOrderAmount((double) amount);
        order.setOrderDate(parseDate((String)data.get("orderDate")));
        order.setClientId((Integer) data.get("clientId"));
        order.setOrderStatus((Integer) data.get("orderStatus"));
        if (data.get("deliveredDate") != null)
            order.setDeliveredDate(parseDate((String)data.get("deliveredDate")));
        List<Map> details = (List<Map>)data.get("orderDetails");
        details.forEach(detail -> order.addOrderDetail(mapOrderDetail(detail)));
        return order;
    }

    public static OrderDetail mapOrderDetail(Map<String, Object> data) {
        OrderDetail detail = new OrderDetail();
        detail.setId((Integer) data.get("id"));
        detail.setOrderId((Integer) data.get("orderId"));
        detail.setProduct((Integer) data.get("product"));
        detail.setProductsAmount((Integer) data.get("productsAmount"));
        return detail;
    }
}
