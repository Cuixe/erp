package org.ceneval.examen.erp.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "[pedido]")
public class Order {

    @Id
    @Column(name = "id_pedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Column(name = "id_cliente")
    private int clientId;

    @Column(name = "fecha_pedido")
    private Date orderDate;

    @Column(name = "fecha_entrega")
    private Date deliveredDate;

    @Column(name = "total")
    private double orderAmount;

    @Column(name = "estado_pedido")
    private int orderStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void addOrderDetail(OrderDetail detail) {
        orderDetails.add(detail);
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
