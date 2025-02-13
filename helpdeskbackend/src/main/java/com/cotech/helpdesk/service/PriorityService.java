package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.priority.PriorityEntity;
import com.cotech.helpdesk.jpa.priority.PriorityRepository;
import com.cotech.helpdesk.model.ConvertableToEntity;
import com.cotech.helpdesk.model.Priority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriorityService implements ConvertableToEntity<Integer, PriorityEntity> {

    private final PriorityRepository priorityRepository;
    @Getter
    private final ModelMapper mapper;

    public ResponseEntity<List<Priority>> getPriorities() {
        try {
            List<PriorityEntity> entities = this.priorityRepository.findAll();
            List<Priority> priorities = new ArrayList<>();
            for (PriorityEntity entity : entities) {
                priorities.add(this.getMapper().map(entity, Priority.class));
            }
            log.trace("Returning priorities");
            return ResponseEntity.ok(priorities);
        } catch (Exception ex) {
            log.error("Failed to get Priorities:", ex);
            throw new RuntimeException("Failed to get priorities");
        }
    }

    @Override
    public PriorityEntity findEntityById(final Integer id) {
        if (id == null) {
            return null;
        }
        return this.priorityRepository.findById(id).orElse(null);
    }
}
