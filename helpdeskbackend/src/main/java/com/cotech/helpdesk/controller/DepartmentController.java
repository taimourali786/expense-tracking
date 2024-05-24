package com.cotech.helpdesk.controller;


import com.cotech.helpdesk.UrlPrefix;
import com.cotech.helpdesk.model.Department;
import com.cotech.helpdesk.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.processing.Generated;
import java.util.List;

@RestController
@RequestMapping(value = UrlPrefix.DEPARTMENT)
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @Generated(value = "/all")
    public List<Department> getDepartments(){
        return this.departmentService.getDepartments();
    }
}
