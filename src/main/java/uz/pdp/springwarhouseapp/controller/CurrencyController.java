package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.entity.Currency;
import uz.pdp.springwarhouseapp.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService service;

    @Autowired
    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Currency>> getAll() {
        return service.getAllCurrency();
    }

    @GetMapping("/id={currencyId}")
    private ResponseEntity<?> getOne(@PathVariable Integer currencyId) {
        return service.getOneCurrency(currencyId);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody Currency currency) {
        return service.addCurrency(currency);
    }

    @PutMapping("/id={currencyId}")
    private ResponseEntity<?> update(@PathVariable Integer currencyId, @RequestBody Currency currency) {
        return service.editCurrency(currencyId, currency);
    }

    @DeleteMapping("/id={currencyId}")
    private ResponseEntity<?> delete(@PathVariable Integer currencyId) {
        return service.deleteCurrency(currencyId);
    }
}
