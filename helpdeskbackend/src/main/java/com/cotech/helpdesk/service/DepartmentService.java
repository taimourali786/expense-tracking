package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.department.DepartmentEntity;
import com.cotech.helpdesk.jpa.department.DepartmentRepository;
import com.cotech.helpdesk.model.Department;
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
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    @Getter
    private final ModelMapper mapper;

    public List<Department> getDepartments() {
        List<DepartmentEntity> entities = this.departmentRepository.findAll();
        if (entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<Department> departments = new ArrayList<>();
        for (DepartmentEntity departmentEntity : entities) {
            departments.add(this.getMapper().map(departmentEntity, Department.class));
        }
        log.trace("Returning departments");
        return departments;
    }
}
