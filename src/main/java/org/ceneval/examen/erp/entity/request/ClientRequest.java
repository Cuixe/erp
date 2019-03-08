package org.ceneval.examen.erp.entity.request;

import org.ceneval.examen.erp.entity.Client;

public class ClientRequest {

    private String name;

    private String address;

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

    public void pupulate(Client client) {
        client.setAddress(this.address);
        client.setName(this.name);
    }
}
