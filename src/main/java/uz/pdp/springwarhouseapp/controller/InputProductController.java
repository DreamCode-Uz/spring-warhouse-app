package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.payload.InputProductDTO;
import uz.pdp.springwarhouseapp.service.InputProductService;

@RestController
@RequestMapping("/api/input/product")
public class InputProductController {

    private final InputProductService service;

    @Autowired
    public InputProductController(InputProductService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getAllInput(page, size);
    }

    @GetMapping("/id={input_product_Id}")
    private ResponseEntity<?> getOne(@PathVariable("input_product_Id") Integer id) {
        return service.getOneInput(id);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody InputProductDTO inputProductDTO) {
        return service.saveInput(inputProductDTO);
    }

    @PutMapping("/id={input_product_Id}")
    private ResponseEntity<?> update(@PathVariable("input_product_Id") Integer id, @RequestBody InputProductDTO inputProductDTO) {
        return service.editInput(id, inputProductDTO);
    }

    @DeleteMapping("/id={input_product_Id}")
    private ResponseEntity<?> delete(@PathVariable("input_product_Id") Integer id) {
        return service.deleteInputProject(id);
    }
}
