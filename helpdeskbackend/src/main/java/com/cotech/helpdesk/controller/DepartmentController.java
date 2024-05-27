package com.cotech.helpdesk.controller;


import com.cotech.helpdesk.UrlPrefix;
import com.cotech.helpdesk.model.Department;
import com.cotech.helpdesk.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = UrlPrefix.DEPARTMENT)
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Department>> getDepartments() {
        return this.departmentService.getDepartments();
    }

    @PostMapping(value = "/create")
    public void insertDepartment(final @RequestBody Department department) {
        this.departmentService.createDepartment(department);
    }

    @PutMapping(value = "/update")
    public void updateDepartment(final @RequestBody Department department) {
        this.departmentService.updateDepartment(department);
    }

    @DeleteMapping(value = "/delete/{departmentId}")
    public void deleteDepartment(final @PathVariable Integer departmentId) {
        this.departmentService.deleteDepartment(departmentId);
    }
}
