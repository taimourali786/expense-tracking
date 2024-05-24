package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.department.DepartmentEntity;
import com.cotech.helpdesk.jpa.department.DepartmentRepository;
import com.cotech.helpdesk.model.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService extends BaseService{

    private DepartmentRepository departmentRepository;

    public List<Department> getDepartments(){
        List<DepartmentEntity> entities = this.departmentRepository.findAll();
        if(entities.isEmpty()){
            return new ArrayList<>();
        }
        List<Department> departments= new ArrayList<>();
        for(DepartmentEntity departmentEntity : entities){
            departments.add(this.getMapper().map(departmentEntity, Department.class));
        }
        return departments;
    }
}
