package org.ceneval.examen.erp.flow;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.ceneval.examen.erp.ErpApplication;
import org.ceneval.examen.erp.entity.Client;
import org.ceneval.examen.erp.utils.JsonMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ErpApplication.class }, webEnvironment
        = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ClientIntegrationTest {

    private static final String API_ROOT
            = "http://localhost:8080/api/client";

    private Response addNewClient(Client client) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT);
    }

    private Client getClientById(int id) {
        Response response = RestAssured.get(API_ROOT + "/" + id);
        return JsonMapper.mapClient(response);
    }

    @Test
    public void findAllClients() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void findOneClient() {
        Client client = getClientById(1);
        assertEquals("Hugo", client.getName());
    }

    @Test
    public void addAndRemove() {
        Response response = addNewClient( new Client("diana", "other direction"));
        Client client = JsonMapper.mapClient(response);
        Assert.assertTrue(client.getId() > 0);

        Client newClient = getClientById(client.getId());

        assertEquals("diana", newClient.getName());
        assertEquals("other direction", newClient.getAddress());

        response = RestAssured.delete(API_ROOT + "/" + client.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(API_ROOT + "/" + client.getId());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

    }

    @Test
    public void updateNewClient() {
        Client client = getClientById(1);
        String originalAddress = client.getAddress();
        client.setAddress("other direction");

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .put(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        client = getClientById(1);
        assertEquals("Hugo Enrique", client.getName());
        assertEquals("other direction", client.getAddress());

        client.setAddress(originalAddress);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .put(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }



}
