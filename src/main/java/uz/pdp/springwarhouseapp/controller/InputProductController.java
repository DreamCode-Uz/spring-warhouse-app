package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springwarhouseapp.service.InputProductService;

@RestController
@RequestMapping("/api/input/product")
public class InputProductController {

    private final InputProductService service;

    @Autowired
    public InputProductController(InputProductService service) {
        this.service = service;
    }
}
