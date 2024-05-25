package com.cotech.helpdesk.controller;

import com.cotech.helpdesk.UrlPrefix;
import com.cotech.helpdesk.model.Category;
import com.cotech.helpdesk.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/subcategories/{categoryId}")
    public ResponseEntity<List<Category>> getSubCategories(final @PathVariable Long categoryId) {
        return this.categoryService.getSubCategories(categoryId);
    }
}
