package uz.pdp.springwarhouseapp.service;

import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.repository.CurrencyRepository;
import uz.pdp.springwarhouseapp.repository.InputRepository;
import uz.pdp.springwarhouseapp.repository.SupplierRepository;
import uz.pdp.springwarhouseapp.repository.WarehouseRepository;

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
}
