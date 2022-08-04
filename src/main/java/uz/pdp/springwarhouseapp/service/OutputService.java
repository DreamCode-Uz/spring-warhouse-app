package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Client;
import uz.pdp.springwarhouseapp.entity.Currency;
import uz.pdp.springwarhouseapp.entity.Output;
import uz.pdp.springwarhouseapp.entity.Warehouse;
import uz.pdp.springwarhouseapp.payload.OutputDTO;
import uz.pdp.springwarhouseapp.repository.ClientRepository;
import uz.pdp.springwarhouseapp.repository.CurrencyRepository;
import uz.pdp.springwarhouseapp.repository.OutputRepository;
import uz.pdp.springwarhouseapp.repository.WarehouseRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class OutputService {

    private final OutputRepository repository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public OutputService(OutputRepository repository, WarehouseRepository warehouseRepository, CurrencyRepository currencyRepository, ClientRepository clientRepository) {
        this.repository = repository;
        this.warehouseRepository = warehouseRepository;
        this.currencyRepository = currencyRepository;
        this.clientRepository = clientRepository;
    }

    //    READ ALL
    public ResponseEntity<List<Output>> getAllOutput() {
        return new ResponseEntity<>(repository.findAll(), OK);
    }

    //    READ ONE
    public ResponseEntity<?> getOneOutput(Integer id) {
        Optional<Output> optionalOutput = repository.findById(id);
        if (!optionalOutput.isPresent()) return new ResponseEntity<>("Output not found.", NOT_FOUND);
        return new ResponseEntity<>(optionalOutput.get(), OK);
    }

    //    CREATE
    public ResponseEntity<?> addOutput(OutputDTO dto) {
        Optional<Warehouse> ow = warehouseRepository.findById(dto.getWarehouseId());
        if (!ow.isPresent()) return new ResponseEntity<>("Warehouse not found.", NOT_FOUND);
        Optional<Currency> oCurrency = currencyRepository.findById(dto.getCurrencyId());
        if (!oCurrency.isPresent()) return new ResponseEntity<>("Currency not found.", NOT_FOUND);
        Optional<Client> oClient = clientRepository.findById(dto.getClientId());
        if (!oClient.isPresent()) return new ResponseEntity<>("Client not found.", NOT_FOUND);
        Optional<Output> maxId = repository.getMaxId();
        String code = String.valueOf(maxId.isPresent() ? maxId.get().getId() + 1 : "1");
        if (dto.getFactureNumber() == null)
            return new ResponseEntity<>("Invoice number must not be empty.", PRECONDITION_REQUIRED);
        Output output = new Output(new Timestamp(new Date().getTime()), ow.get(), oClient.get(), oCurrency.get(), dto.getFactureNumber(), code);
        return new ResponseEntity<>(repository.save(output), CREATED);
    }

    //    UPDATE
    public ResponseEntity<?> editOutput(Integer outputId, OutputDTO dto) {
        Optional<Output> optionalOutput = repository.findById(outputId);
        if (!optionalOutput.isPresent()) return new ResponseEntity<>("Output not found.", NOT_FOUND);
        Output output = optionalOutput.get();
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(dto.getWarehouseId());
        optionalWarehouse.ifPresent(output::setWarehouse);
        Optional<Client> optionalClient = clientRepository.findById(dto.getClientId());
        // TODO: INTELLIJ IDEA BUG
        optionalClient.ifPresent(output::setClient);
        Optional<Currency> optionalCurrency = currencyRepository.findById(dto.getCurrencyId());
        optionalCurrency.ifPresent(output::setCurrency);
        if (dto.getFactureNumber() != null) output.setFactureNumber(dto.getFactureNumber());
        return new ResponseEntity<>(repository.save(output), OK);
    }

    //    DELETE
    public ResponseEntity<?> deleteOutput(Integer outputId) {
        Optional<Output> optionalOutput = repository.findById(outputId);
        if (optionalOutput.isPresent()) {
            try {
                repository.delete(optionalOutput.get());
            } catch (Exception e) {
                return new ResponseEntity<>(BAD_REQUEST);
            }
            return new ResponseEntity<>("Output successfully deleted.", OK);
        }
        return new ResponseEntity<>("Output not found.", NOT_FOUND);
    }
}

