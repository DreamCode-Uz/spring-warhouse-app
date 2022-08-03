package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springwarhouseapp.service.InputService;

@RestController
@RequestMapping("/api/input")
public class InputController {
    private final InputService service;

    @Autowired
    public InputController(InputService service) {
        this.service = service;
    }
}
