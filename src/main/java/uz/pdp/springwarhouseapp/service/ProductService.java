package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Attachment;
import uz.pdp.springwarhouseapp.entity.Category;
import uz.pdp.springwarhouseapp.entity.Measurement;
import uz.pdp.springwarhouseapp.entity.Product;
import uz.pdp.springwarhouseapp.payload.ProductDTO;
import uz.pdp.springwarhouseapp.repository.AttachmentRepository;
import uz.pdp.springwarhouseapp.repository.CategoryRepository;
import uz.pdp.springwarhouseapp.repository.MeasurementRepository;
import uz.pdp.springwarhouseapp.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final MeasurementRepository measurementRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, AttachmentRepository attachmentRepository, MeasurementRepository measurementRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
        this.measurementRepository = measurementRepository;
    }

    //    CREAT
    public ResponseEntity<?> productSave(ProductDTO dto) {
        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(dto.getName(), dto.getCategoryId());
        if (existsByNameAndCategoryId)
            return new ResponseEntity<>("This product is already available in this category.", NOT_FOUND);
//        FOR CATEGORY
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent()) return new ResponseEntity<>("No such category exists.", NOT_FOUND);
//        FOR ATTACHMENT
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getPhotoId());
        if (!optionalAttachment.isPresent()) return new ResponseEntity<>("No such photo exists.", NOT_FOUND);
//        FOR MEASUREMENT
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(dto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new ResponseEntity<>("There is no such unit of measurement", NOT_FOUND);

        Optional<Product> optionalMaxId = productRepository.getMaxId();
        String code = String.valueOf(optionalMaxId.map(value -> value.getId() + 1).orElse(0));
        Product product = new Product(optionalCategory.get(), optionalAttachment.get(), code, optionalMeasurement.get());
        product.setName(dto.getName());
        return new ResponseEntity<>(productRepository.save(product), CREATED);
    }

    //    READ ALL
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), OK);
    }

    //    READ ONE
    public ResponseEntity<?> getOneProduct(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return new ResponseEntity<>(optionalProduct.get(), OK);
        }
        return new ResponseEntity<>("Product not found.", NOT_FOUND);
    }

    //    UPDATE
    public ResponseEntity<?> updateProduct(Integer productId, ProductDTO dto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) return new ResponseEntity<>("Product not found", NOT_FOUND);
        Product product = optionalProduct.get();
        Optional<Category> optionalCategory = categoryRepository.findById(product.getId());
        optionalCategory.ifPresent(product::setCategory);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getPhotoId());
        optionalAttachment.ifPresent(product::setPhoto);
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(dto.getMeasurementId());
        optionalMeasurement.ifPresent(product::setMeasurement);
        if (!productRepository.existsByNameAndCategoryId(dto.getName(), product.getCategory().getId())) {
            product.setName(dto.getName());
        }
        return new ResponseEntity<>(productRepository.save(product), OK);
    }

    //    DELETE
    public ResponseEntity<?> deleteProduct(Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) return new ResponseEntity<>("Product not found.", NOT_FOUND);
        try {
            productRepository.delete(optionalProduct.get());
        } catch (Exception e) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>("Product successfully deleted", OK);
    }
}
