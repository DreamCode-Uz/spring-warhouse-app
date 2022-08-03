package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Warehouse;
import uz.pdp.springwarhouseapp.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class WarehouseService {
    private final WarehouseRepository repository;

    @Autowired
    public WarehouseService(WarehouseRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        return new ResponseEntity<>(repository.findAll(), OK);
    }

    public ResponseEntity<?> getOneWarehouse(Integer warehouseId) {
        Optional<Warehouse> optionalWarehouse = repository.findById(warehouseId);
        if (!optionalWarehouse.isPresent()) return new ResponseEntity<>("Warehouse not found", NOT_FOUND);
        return new ResponseEntity<>(optionalWarehouse.get(), OK);
    }

    public ResponseEntity<?> addWarehouse(Warehouse warehouse) {
        if (warehouse.getName() == null) return new ResponseEntity<>("Warehouse name does not exist", NO_CONTENT);
        return new ResponseEntity<>(repository.save(warehouse), CREATED);
    }

    public ResponseEntity<?> editWarehouse(Integer warehouseId, Warehouse warehouse) {
        Optional<Warehouse> optionalWarehouse = repository.findById(warehouseId);
        if (!optionalWarehouse.isPresent()) return new ResponseEntity<>("Warehouse not found.", NOT_FOUND);
        Warehouse whe = optionalWarehouse.get();
        if (warehouse.getName() != null) whe.setName(warehouse.getName());
        whe.setActive(warehouse.isActive());
        return new ResponseEntity<>(repository.save(whe), OK);
    }

    public ResponseEntity<?> deleteWarehouse(Integer warehouseId) {
        Optional<Warehouse> optionalWarehouse = repository.findById(warehouseId);
        if (!optionalWarehouse.isPresent()) return new ResponseEntity<>("Warehouse not found.", NOT_FOUND);
        try {
            repository.delete(optionalWarehouse.get());
        } catch (Exception e) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>("Warehouse successfully deleted.", OK);
    }
}
