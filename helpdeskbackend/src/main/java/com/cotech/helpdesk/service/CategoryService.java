package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.catagory.CategoryEntity;
import com.cotech.helpdesk.jpa.catagory.CategoryRepository;
import com.cotech.helpdesk.model.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Getter
    private final ModelMapper mapper;

    public ResponseEntity<List<Category>> getCategories() {
        try {
            List<CategoryEntity> entities = this.categoryRepository.findAll();
            log.trace("Returning list of categories");
            return ResponseEntity.ok(this.toCategoryList(entities));
        } catch (Exception ex) {
            log.error("Failed to get categories: ", ex);
            throw new RuntimeException("Failed to get categories");
        }

    }

    public ResponseEntity<List<Category>> getSubCategories(final Long parentId) {
        try {
            List<CategoryEntity> entities = this.categoryRepository
                    .findSubCategories(parentId);
            log.trace("Returning list of subcategories for category id: {}", parentId);
            return ResponseEntity.ok(this.toCategoryList(entities));
        } catch (Exception ex) {
            log.error("Failed to get category with parent id {}:", parentId, ex);
            throw new RuntimeException("Failed to get categories");
        }

    }

    private List<Category> toCategoryList(final List<CategoryEntity> categoryEntities) {
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            categories.add(this.getMapper().map(categoryEntity, Category.class));
        }
        return categories;
    }
}
