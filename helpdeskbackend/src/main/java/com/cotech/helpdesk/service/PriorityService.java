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
public class PriorityService extends BaseService{

    private final PriorityRepository priorityRepository;

    public List<Priority> getAvailablePriorities(){
        List<PriorityEntity> entities = this.priorityRepository.findAll();
        if(entities.isEmpty()){
            return new ArrayList<>();
        }
        List<Priority> priorities = new ArrayList<>();
        for(PriorityEntity entity : entities){
            priorities.add(this.getMapper().map(entity, Priority.class));
        }
        return priorities;
    }
}
