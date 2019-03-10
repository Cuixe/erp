package org.ceneval.examen.erp.entity;

import javax.persistence.*;

@Entity
@Table(name = "Producto")
public class Product {

    @Id
    @Column(name = "id_pedido")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "precio")
    private double price;

    @Column(name = "existencias")
    private int stock;

    @Column(name = "id_unidad_medida")
    private int measurement;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        this.measurement = measurement;
    }
}
