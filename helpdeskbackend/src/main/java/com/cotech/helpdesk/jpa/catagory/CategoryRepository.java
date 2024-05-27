package com.cotech.helpdesk.jpa.catagory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query("SELECT cat FROM CategoryEntity cat WHERE cat.parentCategory.id = :parent_id")
    List<CategoryEntity> findSubCategories(@Param("parent_id") Integer parentCategoryId);

    CategoryEntity findByName(String name);
}
