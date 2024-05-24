package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.catagory.CategoryEntity;
import com.cotech.helpdesk.jpa.catagory.CategoryRepository;
import com.cotech.helpdesk.model.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    public List<Category> getCategories() {
        List<CategoryEntity> entities = this.categoryRepository.findAll();
        log.trace("Returning list of categories");
        return this.toCategoryList(entities);
    }

    public List<Category> getSubCategories(final int parentId) {
        List<CategoryEntity> entities = this.categoryRepository
                .findCategoryEntitiesByParentCategory(parentId);
        log.trace("Returning list of subcategories for category id: {}", parentId);
        return this.toCategoryList(entities);
    }

    private List<Category> toCategoryList(final List<CategoryEntity> categoryEntities) {
        if (categoryEntities.isEmpty()) {
            return new ArrayList<>();
        }
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            categories.add(this.getMapper().map(categoryEntity, Category.class));
        }
        return categories;
    }
}
