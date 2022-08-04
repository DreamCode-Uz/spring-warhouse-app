package uz.pdp.springwarhouseapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Input;
import uz.pdp.springwarhouseapp.entity.InputProduct;
import uz.pdp.springwarhouseapp.entity.Product;
import uz.pdp.springwarhouseapp.payload.InputProductDTO;
import uz.pdp.springwarhouseapp.repository.InputProductRepository;
import uz.pdp.springwarhouseapp.repository.InputRepository;
import uz.pdp.springwarhouseapp.repository.ProductRepository;

import java.time.*;
import java.util.Date;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

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

    public ResponseEntity<Page<InputProduct>> getAllInput(Integer page, Integer size) {
        return new ResponseEntity<>(repository.findAll(PageRequest.of(page > 0 ? page - 1 : page, size)), OK);
    }

    public ResponseEntity<?> getOneInput(Integer inputId) {
        Optional<InputProduct> optionalInputProduct = repository.findById(inputId);
        if (optionalInputProduct.isPresent()) return new ResponseEntity<>(optionalInputProduct.get(), OK);
        return new ResponseEntity<>("Input product not found", NOT_FOUND);
    }

    public ResponseEntity<?> saveInput(InputProductDTO dto) {
        Optional<Product> optionalProduct = productRepository.findById(dto.getProductId());
        if (!optionalProduct.isPresent()) return new ResponseEntity<>("Product not found.", NOT_FOUND);
        Optional<Input> optionalInput = inputRepository.findById(dto.getInputId());
        if (!optionalInput.isPresent()) return new ResponseEntity<>("Input not found", NOT_FOUND);
        if (dto.getAmount() == null || dto.getAmount() <= 0)
            return new ResponseEntity<>("A value must be entered in Amount.", PRECONDITION_REQUIRED);
        if (dto.getPrice() == null || dto.getPrice() < 0) dto.setPrice(0D);
        if (dto.getExpireDate() == null)
            return new ResponseEntity<>("An expiration date must be entered.", PRECONDITION_REQUIRED);
        if (!expireCheck(dto.getExpireDate()))
            return new ResponseEntity<>("The minimum shelf life of the product should be 1 day", BAD_REQUEST);
        InputProduct ip = new InputProduct(optionalProduct.get(), dto.getAmount(), dto.getPrice(), dto.getExpireDate(), optionalInput.get());
        return new ResponseEntity<>(repository.save(ip), CREATED);
    }

    public ResponseEntity<?> editInput(Integer id, InputProductDTO dto) {
        Optional<InputProduct> oip = repository.findById(id);
        if (!oip.isPresent()) return new ResponseEntity<>("Input product not found", NOT_FOUND);
        InputProduct inputProduct = oip.get();
        Optional<Input> optionalInput = inputRepository.findById(dto.getInputId());
        optionalInput.ifPresent(inputProduct::setInput);
        Optional<Product> optionalProduct = productRepository.findById(dto.getProductId());
        optionalProduct.ifPresent(inputProduct::setProduct);
        if (dto.getAmount() != null && dto.getAmount() > 0) inputProduct.setAmount(dto.getAmount());
        if (dto.getExpireDate() != null && expireCheck(dto.getExpireDate()))
            inputProduct.setExpireDate(dto.getExpireDate());
        try {
            return new ResponseEntity<>(repository.save(inputProduct), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteInputProject(Integer id) {
        Optional<InputProduct> optionalInputProduct = repository.findById(id);
        if (optionalInputProduct.isPresent()) {
            try {
                repository.delete(optionalInputProduct.get());
            } catch (Exception e) {
                return new ResponseEntity<>(BAD_REQUEST);
            }
            return new ResponseEntity<>("Input project deleted.", OK);
        }
        return new ResponseEntity<>("Input project not found.", NOT_FOUND);
    }

    //    ACTIONS
    public boolean expireCheck(Date expireDate) {
        LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                expireLocalDate = expireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period between = Period.between(now, expireLocalDate);
        return !between.isNegative() && !between.isZero();
    }

    public static void main(String[] args) {
    }
}
