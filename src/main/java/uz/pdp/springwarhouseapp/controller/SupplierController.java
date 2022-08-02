package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.entity.Supplier;
import uz.pdp.springwarhouseapp.service.SupplierService;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    private final SupplierService service;

    @Autowired
    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<?> getAll() {
        return service.getAllSupplier();
    }

    @GetMapping("/id={supplierId}")
    private ResponseEntity<?> getOne(@PathVariable("supplierId") Integer id) {
        return service.getOneSupplier(id);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody Supplier supplier) {
        return service.addNewSupplier(supplier);
    }

    @PutMapping("/id={supplierId}")
    private ResponseEntity<?> update(@PathVariable("supplierId") Integer supplierId, @RequestBody Supplier supplier) {
        return service.editSupplier(supplierId, supplier);
    }

    @DeleteMapping("/id={supplierId}")
    private ResponseEntity<?> delete(@PathVariable("supplierId") Integer supplierId) {
        return service.deleteSupplier(supplierId);
    }
}
