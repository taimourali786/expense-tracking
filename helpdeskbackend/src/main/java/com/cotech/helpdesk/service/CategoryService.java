package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.catagory.CategoryEntity;
import com.cotech.helpdesk.jpa.catagory.CategoryRepository;
import com.cotech.helpdesk.jpa.department.DepartmentEntity;
import com.cotech.helpdesk.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final DepartmentService departmentService;

    public CategoryService(final CategoryRepository categoryRepository,
                           final DepartmentService departmentService,
                           final ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.departmentService = departmentService;
        this.mapper = mapper;
        TypeMap<CategoryEntity, Category> typeMap =
                this.mapper.createTypeMap(CategoryEntity.class, Category.class);
        typeMap.addMapping(src -> src.getParentCategory().getId(), Category::setParentCategoryId);
        typeMap.addMapping(src -> src.getDepartment().getId(), Category::setDepartmentId);

        Converter<Integer, CategoryEntity> categoryEntityConverter = c -> {
            Integer catId = c.getSource();
            if (catId != null) {
                return this.categoryRepository.findById(c.getSource()).orElse(null);
            }
            return null;
        };
        Converter<Integer, DepartmentEntity> departmentEntityConverter = d -> {
            Integer depId = d.getSource();
            if (depId != null) {
                return departmentService.getDepartmentById(depId);
            }
            return null;
        };
        PropertyMap<Category, CategoryEntity> categoryMap = new PropertyMap<>() {
            protected void configure() {
                map().setName(source.getName());
                using(categoryEntityConverter).map(source.getParentCategoryId()).setParentCategory(null);
                using(departmentEntityConverter).map(source.getDepartmentId()).setDepartment(null);
            }
        };
        mapper.addMappings(categoryMap);
    }

    public ResponseEntity<List<Category>> getCategories() {
        try {
            List<CategoryEntity> entities = this.categoryRepository.findAll();
            log.trace("Returning list of categories");
            return ResponseEntity.ok(this.toCategoryList(entities));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get categories", ex);
        }

    }

    public ResponseEntity<List<Category>> getSubCategories(final Integer parentId) {
        try {
            List<CategoryEntity> entities = this.categoryRepository.findSubCategories(parentId);
            log.trace("Returning list of subcategories for category id: {}", parentId);
            return ResponseEntity.ok(this.toCategoryList(entities));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get categories", ex);
        }

    }

    public ResponseEntity<List<Category>> getCategoryByDepId(final Integer depId) {
        try {
            List<CategoryEntity> entities = this.categoryRepository.findCategoryByDepartments(depId);
            log.trace("Returning list of categories by department id:{}", depId);
            return ResponseEntity.ok(this.toCategoryList(entities));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get categories for department", ex);
        }
    }

    public void insertCategory(final Category category) {
        try {
            CategoryEntity entity = this.categoryRepository.findByName(category.getName());
            if (entity != null) {
                log.trace("Failed! Category Already Exists");
                return;
            }
            entity = this.mapper.map(category, CategoryEntity.class);
            this.categoryRepository.save(entity);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to insert category", ex);
        }
    }

    public void updateCategory(Category category) {
        try {
            CategoryEntity entity = this.categoryRepository.findByName(category.getName());
            if (entity == null) {
                log.trace("Category Does not Exists, Cannot be updated");
                return;
            }
            entity.setDepartment(this.departmentService.getDepartmentById(category.getDepartmentId()));
            entity.setParentCategory(this.categoryRepository.findById(category.getParentCategoryId()).orElse(null));
            this.categoryRepository.save(entity);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update category", ex);
        }

    }

    public void deleteCategory(final Integer categoryId) {
        try {
            CategoryEntity entity = this.categoryRepository.findById(categoryId).orElse(null);
            if (entity == null) {
                log.trace("Category Does not Exists");
                return;
            }
            this.categoryRepository.delete(entity);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete category", ex);
        }

    }

    private List<Category> toCategoryList(final List<CategoryEntity> categoryEntities) {
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            categories.add(this.mapper.map(categoryEntity, Category.class));
        }
        return categories;
    }
}
