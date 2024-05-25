package com.cotech.helpdesk.controller;


import com.cotech.helpdesk.UrlPrefix;
import com.cotech.helpdesk.model.Department;
import com.cotech.helpdesk.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
