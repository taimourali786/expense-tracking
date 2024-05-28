package com.cotech.helpdesk.jpa.catagory;

import com.cotech.helpdesk.jpa.department.DepartmentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "helpdesk", name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", nullable = true)
    private CategoryEntity parentCategory;
}
