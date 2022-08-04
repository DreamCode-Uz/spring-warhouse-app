package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.payload.OutputProductDTO;
import uz.pdp.springwarhouseapp.service.OutputProductService;

@RestController
@RequestMapping("/api/output/product")
public class OutputProductController {

    private final OutputProductService service;

    @Autowired
    public OutputProductController(OutputProductService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getAllOutputProduct(page, size);
    }

    @GetMapping("/id={outputProductId}")
    private ResponseEntity<?> getOne(@PathVariable("outputProductId") Integer id) {
        return service.getOneOutputProduct(id);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody OutputProductDTO outputProductDTO) {
        return service.addOutputProduct(outputProductDTO);
    }

    @PutMapping("/id={outputProductId}")
    private ResponseEntity<?> update(@PathVariable("outputProductId") Integer id, @RequestBody OutputProductDTO outputProductDTO) {
        return service.editOutputProduct(id, outputProductDTO);
    }

    @DeleteMapping("/id={outputProductId}")
    private ResponseEntity<?> delete(@PathVariable("outputProductId") Integer id) {
        return service.deleteOutput(id);
    }
}
