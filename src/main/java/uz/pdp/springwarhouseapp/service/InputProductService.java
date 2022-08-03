package uz.pdp.springwarhouseapp.service;

import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.repository.InputProductRepository;
import uz.pdp.springwarhouseapp.repository.InputRepository;
import uz.pdp.springwarhouseapp.repository.ProductRepository;

@Service
public class InputProductService {
    private final InputProductRepository repository;
    private final ProductRepository productRepository;
    private final InputRepository inputRepository;

    public InputProductService(InputProductRepository repository, ProductRepository productRepository, InputRepository inputRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.inputRepository = inputRepository;
    }
}
