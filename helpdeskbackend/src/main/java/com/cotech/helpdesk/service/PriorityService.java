package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.priority.PriorityEntity;
import com.cotech.helpdesk.jpa.priority.PriorityRepository;
import com.cotech.helpdesk.jpa.status.StatusEntity;
import com.cotech.helpdesk.model.Priority;
import com.cotech.helpdesk.model.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriorityService {

    private final PriorityRepository priorityRepository;
    private final ModelMapper mapper;

    public List<Priority> getAvailablePriorities(){
        List<PriorityEntity> entities = this.priorityRepository.findAll();
        List<Priority> priorities = new ArrayList<>();
        for(PriorityEntity entity : entities){
            priorities.add(this.mapper.map(entity, Priority.class));
        }
        return priorities;
    }
}
