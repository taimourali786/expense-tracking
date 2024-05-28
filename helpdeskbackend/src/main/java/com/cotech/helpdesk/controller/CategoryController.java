package com.cotech.helpdesk.controller;

import com.cotech.helpdesk.UrlPrefix;
import com.cotech.helpdesk.model.Category;
import com.cotech.helpdesk.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = UrlPrefix.CATEGORY)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Category>> getCategories() {
        return this.categoryService.getCategories();
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCategory(final @RequestBody Category category) {
        this.categoryService.insertCategory(category);
    }

    @PutMapping(value = "update")
    public void updateCategory(final @RequestBody Category category) {
        this.categoryService.updateCategory(category);
    }

    @DeleteMapping(value = "/delete/{categoryId}")
    public void delete(final @PathVariable Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }

    @GetMapping(value = "/subcategories/{parentCategoryId}")
    public ResponseEntity<List<Category>> getSubCategories(final @PathVariable Integer parentCategoryId) {
        return this.categoryService.getSubCategories(parentCategoryId);
    }
}
