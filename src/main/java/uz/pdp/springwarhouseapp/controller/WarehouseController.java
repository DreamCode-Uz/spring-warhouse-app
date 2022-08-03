package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.entity.Warehouse;
import uz.pdp.springwarhouseapp.service.WarehouseService;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService service;

    @Autowired
    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<?> getAll() {
        return service.getAllWarehouses();
    }

    @GetMapping("/id={warehouseId}")
    private ResponseEntity<?> getOne(@PathVariable("warehouseId") Integer warehouseId) {
        return service.getOneWarehouse(warehouseId);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody Warehouse warehouse) {
        return service.addWarehouse(warehouse);
    }

    @PutMapping("/id={warehouseId}")
    private ResponseEntity<?> update(@PathVariable("warehouseId") Integer warehouseId, @RequestBody Warehouse warehouse) {
        return service.editWarehouse(warehouseId, warehouse);
    }

    @DeleteMapping("/id={warehouseId}")
    private ResponseEntity<?> delete(@PathVariable("warehouseId") Integer warehouseId) {
        return service.deleteWarehouse(warehouseId);
    }
}
