package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.payload.ProductDTO;
import uz.pdp.springwarhouseapp.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    //    CREAT
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) {
        return service.productSave(productDTO);
    }

    //    READ ALL
    @GetMapping
    public ResponseEntity<?> getAll() {
        return service.getAllProducts();
    }

    //    READ ONE
    @GetMapping("/id={productId}")
    private ResponseEntity<?> getOne(@PathVariable Integer productId) {
        return service.getOneProduct(productId);
    }

    //    UPDATE
    @PutMapping("/id={productId}")
    private ResponseEntity<?> update(@PathVariable Integer productId, @RequestBody ProductDTO productDTO) {
        return service.updateProduct(productId, productDTO);
    }

    //    DELETE
    @DeleteMapping("/id={productId}")
    private ResponseEntity<?> delete(@PathVariable Integer productId) {
        return service.deleteProduct(productId);
    }
}
