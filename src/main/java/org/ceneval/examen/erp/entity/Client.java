package org.ceneval.examen.erp.entity;


import javax.persistence.*;

@Entity
@Table(name = "[cliente]")
public class Client {

    @Id
    @Column(name = "id_cliente")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "direccion")
    private String address;

    public Client() {
    }

    public Client(String name, String address) {
        this.name = name;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
