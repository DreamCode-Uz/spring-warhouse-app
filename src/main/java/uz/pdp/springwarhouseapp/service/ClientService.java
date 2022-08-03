package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Client;
import uz.pdp.springwarhouseapp.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class ClientService {
    private final ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(repository.findAll(), OK);
    }

    public ResponseEntity<?> getOneClient(Integer clientId) {
        Optional<Client> optionalClient = repository.findById(clientId);
        if (!optionalClient.isPresent()) {
            return new ResponseEntity<>("Client not found.", NOT_FOUND);
        }
        return new ResponseEntity<>(optionalClient.get(), OK);
    }

    public ResponseEntity<?> addClient(Client client) {
        if (repository.existsByPhoneNumber(client.getPhoneNumber())) {
            return new ResponseEntity<>("This phone number is already registered", ALREADY_REPORTED);
        }
        return new ResponseEntity<>(repository.save(client), CREATED);
    }

    public ResponseEntity<?> editClient(Integer clientId, Client client) {
        Optional<Client> optionalClient = repository.findById(clientId);
        if (!optionalClient.isPresent()) return new ResponseEntity<>("Client not found", NOT_FOUND);
        if (repository.existsByIdNotAndPhoneNumber(clientId, client.getPhoneNumber())) return new ResponseEntity<>("This phone number is already registered", ALREADY_REPORTED);
        Client ct = optionalClient.get();
        ct.setPhoneNumber(client.getPhoneNumber());
        ct.setActive(client.isActive());
        ct.setName(client.getName());
        return new ResponseEntity<>(repository.save(ct), OK);
    }

    public ResponseEntity<?> deleteClient(Integer clientId) {
        Optional<Client> optionalClient = repository.findById(clientId);
        if (!optionalClient.isPresent()) return new ResponseEntity<>("Client not found", NOT_FOUND);
        repository.delete(optionalClient.get());
        return new ResponseEntity<>("Client successfully deleted.", OK);
    }
}
