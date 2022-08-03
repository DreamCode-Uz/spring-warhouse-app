package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.payload.InputDTO;
import uz.pdp.springwarhouseapp.service.InputService;

@RestController
@RequestMapping("/api/input")
public class InputController {
    private final InputService service;

    @Autowired
    public InputController(InputService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        return service.getAllInputs(page);
    }

    @GetMapping("/id={inputId}")
    private ResponseEntity<?> getOne(@PathVariable("inputId") Integer id) {
        return service.getOneInputs(id);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody InputDTO inputDTO) {
        return service.saveInput(inputDTO);
    }

    @PutMapping("/id={inputId}")
    private ResponseEntity<?> edit(@PathVariable("inputId") Integer id, @RequestBody InputDTO inputDTO) {
        return service.editInput(id, inputDTO);
    }

    @DeleteMapping("/id={inputId}")
    private ResponseEntity<?> delete(@PathVariable("inputId") Integer id) {
        return service.deleteInput(id);
    }
}
