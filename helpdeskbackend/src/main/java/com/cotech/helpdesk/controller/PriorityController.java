package com.cotech.helpdesk.controller;

import com.cotech.helpdesk.UrlPrefix;
import com.cotech.helpdesk.model.Priority;
import com.cotech.helpdesk.service.PriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = UrlPrefix.PRIORITY)
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityService priorityService;

    @GetMapping(value = "/all")
    public List<Priority> getPriorities() {
        return this.priorityService.getAvailablePriorities();
    }
}
