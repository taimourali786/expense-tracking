package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.status.StatusEntity;
import com.cotech.helpdesk.jpa.status.StatusRepository;
import com.cotech.helpdesk.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusService extends BaseService {

    private final StatusRepository statusRepository;

    public List<Status> getStatuses() {
        List<StatusEntity> entities = this.statusRepository.findAll();
        if (entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<Status> statusList = new ArrayList<>();
        for (StatusEntity entity : entities) {
            statusList.add(this.getMapper().map(entity, Status.class));
        }
        log.trace("Returning statuses");
        return statusList;
    }
}
