package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Supplier;
import uz.pdp.springwarhouseapp.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    @Autowired
    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> addNewSupplier(Supplier supplier) {
        if (supplier.getPhoneNumber() != null && repository.existsByPhoneNumber(supplier.getPhoneNumber())) {
            return new ResponseEntity<>(repository.save(supplier), CREATED);
        }
        return new ResponseEntity<>(BAD_REQUEST);
    }

    public ResponseEntity<List<Supplier>> getAllSupplier() {
        return new ResponseEntity<>(repository.findAll(), OK);
    }

    public ResponseEntity<?> getOneSupplier(Integer id) {
        Optional<Supplier> optionalSupplier = repository.findById(id);
        if (!optionalSupplier.isPresent()) {
            return new ResponseEntity<>("Supplier not found", NOT_FOUND);
        }
        return new ResponseEntity<>(optionalSupplier.get(), OK);
    }

    public ResponseEntity<?> editSupplier(Integer supplierId, Supplier supplier) {
        Optional<Supplier> optionalSupplier = repository.findById(supplierId);
        if (!optionalSupplier.isPresent()) return new ResponseEntity<>("Supplier not found", NOT_FOUND);
        Supplier spr = optionalSupplier.get();
        spr.setActive(supplier.isActive());
        spr.setName(supplier.getName());
        if (!repository.existsByIdNotAndPhoneNumber(supplierId, supplier.getPhoneNumber())) {
            spr.setPhoneNumber(supplier.getPhoneNumber());
        }
        return new ResponseEntity<>(repository.save(spr), OK);
    }

    public ResponseEntity<?> deleteSupplier(Integer id) {
        Optional<Supplier> optionalSupplier = repository.findById(id);
        if (!optionalSupplier.isPresent()) return new ResponseEntity<>("Supplier not found", NOT_FOUND);
        repository.delete(optionalSupplier.get());
        return new ResponseEntity<>("Supplier successfully deleted.", OK);
    }
}
