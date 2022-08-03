package uz.pdp.springwarhouseapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Currency;
import uz.pdp.springwarhouseapp.entity.Input;
import uz.pdp.springwarhouseapp.entity.Supplier;
import uz.pdp.springwarhouseapp.entity.Warehouse;
import uz.pdp.springwarhouseapp.payload.InputDTO;
import uz.pdp.springwarhouseapp.repository.CurrencyRepository;
import uz.pdp.springwarhouseapp.repository.InputRepository;
import uz.pdp.springwarhouseapp.repository.SupplierRepository;
import uz.pdp.springwarhouseapp.repository.WarehouseRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class InputService {
    private final InputRepository repository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final CurrencyRepository currencyRepository;

    public InputService(InputRepository repository, WarehouseRepository warehouseRepository, SupplierRepository supplierRepository, CurrencyRepository currencyRepository) {
        this.repository = repository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
        this.currencyRepository = currencyRepository;
    }

    public ResponseEntity<Page<Input>> getAllInputs(Integer page) {
        return new ResponseEntity<>(repository.findAll(PageRequest.of(page > 0 ? page - 1 : 0, 10)), OK);
    }

    public ResponseEntity<?> getOneInputs(Integer inputId) {
        Optional<Input> optionalInput = repository.findById(inputId);
        if (!optionalInput.isPresent()) return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(optionalInput.get(), OK);
    }

    public ResponseEntity<?> saveInput(InputDTO dto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(dto.getWarehouseId());
        Optional<Supplier> optionalSupplier = supplierRepository.findById(dto.getSupplierId());
        Optional<Currency> optionalCurrency = currencyRepository.findById(dto.getCurrencyId());
        if (!optionalWarehouse.isPresent() || !optionalSupplier.isPresent() || !optionalCurrency.isPresent())
            return new ResponseEntity<>(NOT_FOUND);
        Optional<Input> maxId = repository.getMaxId();
        String code = String.valueOf(maxId.map(input -> input.getId() + 1).orElse(1));
        Input input = new Input(new Timestamp(new Date().getTime()), optionalWarehouse.get(), optionalSupplier.get(), optionalCurrency.get(), dto.getFactureNumber(), code);
        return new ResponseEntity<>(repository.save(input), CREATED);
    }

    public ResponseEntity<?> editInput(Integer id, InputDTO dto) {
        Optional<Input> optionalInput = repository.findById(id);
        if (!optionalInput.isPresent()) return new ResponseEntity<>(NOT_FOUND);
        Input input = optionalInput.get();
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(dto.getWarehouseId());
        optionalWarehouse.ifPresent(input::setWarehouse);
        Optional<Supplier> optionalSupplier = supplierRepository.findById(dto.getSupplierId());
        optionalSupplier.ifPresent(input::setSupplier);
        Optional<Currency> optionalCurrency = currencyRepository.findById(dto.getCurrencyId());
        optionalCurrency.ifPresent(input::setCurrency);
        if (dto.getFactureNumber() != null) input.setFactureNumber(dto.getFactureNumber());
        return new ResponseEntity<>(repository.save(input), OK);
    }

    public ResponseEntity<?> deleteInput(Integer id) {
        Optional<Input> optionalInput = repository.findById(id);
        if (!optionalInput.isPresent()) return new ResponseEntity<>(NOT_FOUND);
        try {
            repository.delete(optionalInput.get());
        } catch (Exception e) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>("Input element successfully deleted.", OK);
    }
}
