package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.entity.Client;
import uz.pdp.springwarhouseapp.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Client>> getAll() {
        return service.getAllClients();
    }

    @GetMapping("/id={clientId}")
    private ResponseEntity<?> getOne(@PathVariable("clientId") Integer clientId) {
        return service.getOneClient(clientId);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody Client client) {
        return service.addClient(client);
    }

    @PutMapping("/id={clientId}")
    private ResponseEntity<?> update(@PathVariable("clientId") Integer clientId, @RequestBody Client client) {
        return service.editClient(clientId, client);
    }

    @DeleteMapping("/id={clientId}")
    private ResponseEntity<?> delete(@PathVariable("clientId") Integer clientId) {
        return service.deleteClient(clientId);
    }
}
