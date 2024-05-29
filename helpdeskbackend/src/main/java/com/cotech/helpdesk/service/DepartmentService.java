package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.department.DepartmentEntity;
import com.cotech.helpdesk.jpa.department.DepartmentRepository;
import com.cotech.helpdesk.jpa.user.UserEntity;
import com.cotech.helpdesk.model.ConvertableToEntity;
import com.cotech.helpdesk.model.Department;
import com.cotech.helpdesk.util.ModelMapperUtil;
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
public class DepartmentService implements ConvertableToEntity<Integer, DepartmentEntity> {

    private final ModelMapper mapper;
    private final DepartmentRepository departmentRepository;
    private final UserService userService;


    public DepartmentService(final DepartmentRepository departmentRepository,
                             final UserService userService,
                             final ModelMapper mapper) {
        this.userService = userService;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
        TypeMap<DepartmentEntity, Department> type =
                this.mapper.createTypeMap(DepartmentEntity.class, Department.class);
        type.addMapping(src -> src.getLead().getId(), Department::setLeadId);
        Converter<Integer, UserEntity> userEntityConverter =
                ModelMapperUtil.createConverter(userService);
        PropertyMap<Department, DepartmentEntity> departmentMap = new PropertyMap<>() {
            protected void configure() {
                map().setName(source.getName());
                using(userEntityConverter).map(source.getLeadId()).setLead(null);
            }
        };

        mapper.addMappings(departmentMap);
    }

    public ResponseEntity<List<Department>> getDepartments() {
        try {
            List<DepartmentEntity> entities = this.departmentRepository.findAll();
            List<Department> departments = new ArrayList<>();
            for (DepartmentEntity departmentEntity : entities) {
                departments.add(this.mapper.map(departmentEntity, Department.class));
            }
            log.trace("Returning departments");
            return ResponseEntity.ok(departments);
        } catch (Exception ex) {
            log.error("Failed to get departments: ", ex);
            throw new RuntimeException("Failed to get departments");
        }

    }

    public void createDepartment(final Department department) {
        try {
            DepartmentEntity entity = this.departmentRepository.findByName(department.getName())
                    .orElse(null);
            if (entity != null) {
                log.debug("Department Already exists");
                return;
            }
            DepartmentEntity departmentEntity = this.mapper.map(department, DepartmentEntity.class);
            this.departmentRepository.save(departmentEntity);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to insert department", ex);
        }
    }

    public void updateDepartment(final Department department) {
        try {
            DepartmentEntity entity = this.departmentRepository.findByName(department.getName())
                    .orElse(null);
            if (entity == null) {
                log.debug("Department does not exists");
                return;
            }
            entity.setLead(this.userService.findEntityById(department.getLeadId()));
            this.departmentRepository.save(entity);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to insert department", ex);
        }
    }

    public void deleteDepartment(final Integer departmentId) {
        try {
            DepartmentEntity entity = this.departmentRepository.findById(departmentId).orElse(null);
            if (entity == null) {
                log.debug("Department does no exists");
                return;
            }
            this.departmentRepository.delete(entity);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to insert department", ex);
        }
    }

    @Override
    public DepartmentEntity findEntityById(final Integer id) {
        if (id == null) {
            return null;
        }
        return this.departmentRepository.findById(id).orElse(null);
    }
}
