package org.ceneval.examen.erp.controller;

import org.ceneval.examen.erp.entity.Client;
import org.ceneval.examen.erp.entity.request.ClientRequest;
import org.ceneval.examen.erp.exception.ClientNotFoundException;
import org.ceneval.examen.erp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }


    @GetMapping("/{id}")
    public Client findOne(@PathVariable int id) {
        return clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException:: new);
    }

    @PostMapping()
    public Client addClient(@RequestBody ClientRequest clientRequest) {
        Client client = new Client();
        clientRequest.pupulate(client);
        clientRepository.save(client);
        return client;
    }

    @PutMapping()
    public Client update(@RequestBody Client client) {
        clientRepository.findById(client.getId())
                .orElseThrow(ClientNotFoundException::new);
        return clientRepository.save(client);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        clientRepository.deleteById(id);
    }

}
