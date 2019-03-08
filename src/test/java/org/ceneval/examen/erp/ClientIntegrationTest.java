package org.ceneval.examen.erp;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.ceneval.examen.erp.entity.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ErpApplication.class }, webEnvironment
        = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ClientIntegrationTest {

    private static final String API_ROOT
            = "http://localhost:8080/api/client";

    private int addNewClient(Client client) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT);
        return response.jsonPath().getInt("id");
    }

    private Client getClient(int id) {
        Response response = RestAssured.get(API_ROOT + "/" + id);
        Client client = new Client(response.jsonPath().get("name"), response.jsonPath().get("address"));
        client.setId(response.jsonPath().get("id"));
        return client;
    }

    @Test
    public void findAllClients() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void findOneClient() {
        Client client = getClient(1);
        assertEquals("Hugo", client.getName());
    }

    @Test
    public void addNewClient() {
        int newId = addNewClient( new Client("diana", "other direction"));
        Client client = getClient(newId);
        assertEquals("diana", client.getName());
        assertEquals("other direction", client.getAddress());

        Response response = RestAssured.delete(API_ROOT + "/" + newId);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void updateNewClient() {
        Client oldClient = getClient(1);
        Client client = new Client("Hugo Enrique", "other direction");
        client.setId(1);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .put(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Client newClient = getClient(1);
        assertEquals("Hugo Enrique", newClient.getName());
        assertEquals("other direction", newClient.getAddress());

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(oldClient)
                .put(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }


}
