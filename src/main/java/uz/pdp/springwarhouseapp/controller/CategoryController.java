package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.entity.Category;
import uz.pdp.springwarhouseapp.payload.CategoryDTO;
import uz.pdp.springwarhouseapp.payload.Result;
import uz.pdp.springwarhouseapp.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

//    CREATE
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CategoryDTO categoryDTO) {
        return service.addCategory(categoryDTO);
    }

//    READ ALL
    @GetMapping
    public ResponseEntity<?> getAll() {
        return service.getAllCategory();
    }

//    READ ONE
    @GetMapping("/id={categoryId}")
    public ResponseEntity<?> getOne(@PathVariable Integer categoryId) {
        return service.getOneCategory(categoryId);
    }

//    UPDATE
    @PutMapping("/id={categoryId}")
    public ResponseEntity<?> update(@PathVariable Integer categoryId, @RequestBody CategoryDTO categoryDTO) {
        return service.editCategory(categoryId, categoryDTO);
    }

//    DELETE
    @DeleteMapping("/id={categoryId}")
    public ResponseEntity<?> delete(@PathVariable Integer categoryId) {
        return service.deleteCategory(categoryId);
    }
}
