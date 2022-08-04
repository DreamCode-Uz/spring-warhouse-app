package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.payload.OutputDTO;
import uz.pdp.springwarhouseapp.service.OutputService;

@RestController
@RequestMapping("/api/output")
public class OutputController {
    private final OutputService service;

    @Autowired
    public OutputController(OutputService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<?> getAll() {
        return service.getAllOutput();
    }

    @GetMapping("/id={outputId}")
    private ResponseEntity<?> getOne(@PathVariable("outputId") Integer id) {
        return service.getOneOutput(id);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody OutputDTO outputDTO) {
        return service.addOutput(outputDTO);
    }

    @PutMapping("/id={outputId}")
    private ResponseEntity<?> update(@PathVariable("outputId") Integer id, @RequestBody OutputDTO outputDTO) {
        return service.editOutput(id, outputDTO);
    }

    @DeleteMapping("/id={outputId}")
    private ResponseEntity<?> delete(@PathVariable("outputId") Integer id) {
        return service.deleteOutput(id);
    }
}
