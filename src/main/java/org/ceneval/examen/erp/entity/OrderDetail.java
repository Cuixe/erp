package org.ceneval.examen.erp.entity;

import javax.persistence.*;

@Entity
@Table(name = "[detalle_pedido]")
public class OrderDetail {

    @Id
    @Column(name = "id_detalle")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_pedido")
    private int orderId;

    @Column(name = "id_producto")
    private int product;

    @Column(name = "cantidad")
    private int productsAmount;

    public OrderDetail() {
    }

    public OrderDetail(int product, int productsAmount) {
        this.product = product;
        this.productsAmount = productsAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductsAmount() {
        return productsAmount;
    }

    public void setProductsAmount(int productsAmount) {
        this.productsAmount = productsAmount;
    }
}
