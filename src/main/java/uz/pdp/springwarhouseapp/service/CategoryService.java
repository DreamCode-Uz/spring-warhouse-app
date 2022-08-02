package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Category;
import uz.pdp.springwarhouseapp.payload.CategoryDTO;
import uz.pdp.springwarhouseapp.repository.CategoryRepository;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    //    CREATE
    public ResponseEntity<?> addCategory(CategoryDTO dto) {
        Category category = new Category();
        if (repository.existsByName(dto.getName()))
            return new ResponseEntity<>("Category name already exist.", ALREADY_REPORTED);
        category.setName(dto.getName());
        if (dto.getCategoryId() != null) {
            Optional<Category> optionalCategory = repository.findById(dto.getCategoryId());
            if (!optionalCategory.isPresent()) return new ResponseEntity<>("Category not found.", NOT_FOUND);
            category.setParentCategory(optionalCategory.get());
        }
        return new ResponseEntity<>(repository.save(category), CREATED);
    }

    //    READ ALL
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<>(repository.findAll(), OK);
    }

    //    READ ONE
    public ResponseEntity<?> getOneCategory(Integer id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isPresent()) return new ResponseEntity<>(optionalCategory.get(), OK);
        return new ResponseEntity<>("Category not found.", NOT_FOUND);
    }

    //    EDIT
    public ResponseEntity<?> editCategory(Integer id, CategoryDTO dto) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (!optionalCategory.isPresent()) return new ResponseEntity<>("Category not found.", NOT_FOUND);
        if (repository.existsByIdNotAndName(id, dto.getName()))
            return new ResponseEntity<>("Name is already listed for another category", ALREADY_REPORTED);
        Category category = optionalCategory.get();
        category.setName(dto.getName());
        if (dto.getCategoryId() != null && !dto.getCategoryId().equals(category.getId())) {
            Optional<Category> optional = repository.findById(dto.getCategoryId());
            optional.ifPresent(category::setParentCategory);
        }
        return new ResponseEntity<>(repository.save(category), OK);
    }

    //    DELETE
    public ResponseEntity<?> deleteCategory(Integer id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isPresent()) {
            try {
                repository.delete(optionalCategory.get());
                return new ResponseEntity<>("Category successfully deleted.", OK);
            } catch (Exception e) {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Category not found.", NOT_FOUND);
    }
}
