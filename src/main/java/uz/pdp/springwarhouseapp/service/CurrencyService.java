package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Currency;
import uz.pdp.springwarhouseapp.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class CurrencyService {
    private final CurrencyRepository repository;

    @Autowired
    public CurrencyService(CurrencyRepository repository) {
        this.repository = repository;
    }

    //    CREATE
    public ResponseEntity<?> addCurrency(Currency currency) {
        if (repository.existsByName(currency.getName()))
            return new ResponseEntity<>("Currency name already exist", ALREADY_REPORTED);
        return new ResponseEntity<>(repository.save(currency), CREATED);
    }

    //    READ ALL
    public ResponseEntity<List<Currency>> getAllCurrency() {
        return new ResponseEntity<>(repository.findAll(), OK);
    }

    //    READ ONE
    public ResponseEntity<?> getOneCurrency(Integer id) {
        Optional<Currency> optionalCurrency = repository.findById(id);
        if (!optionalCurrency.isPresent()) return new ResponseEntity<>("Currency not found.", NOT_FOUND);
        return new ResponseEntity<>(optionalCurrency.get(), OK);
    }

    //    UPDATE
    public ResponseEntity<?> editCurrency(Integer id, Currency currency) {
        Optional<Currency> optionalCurrency = repository.findById(id);
        if (!optionalCurrency.isPresent()) return new ResponseEntity<>("Currency not found.", NOT_FOUND);
        Currency cry = optionalCurrency.get();
        if (repository.existsByIdNotAndName(id, currency.getName())) {
            cry.setName(currency.getName());
        }
        cry.setActive(currency.isActive());
        return new ResponseEntity<>(repository.save(cry), OK);
    }

    //    DELETE
    public ResponseEntity<?> deleteCurrency(Integer id) {
        Optional<Currency> optionalCurrency = repository.findById(id);
        if (!optionalCurrency.isPresent()) return new ResponseEntity<>("Currency not found.", NOT_FOUND);
        repository.delete(optionalCurrency.get());
        return new ResponseEntity<>("Currency successfully deleted.", OK);
    }
}
