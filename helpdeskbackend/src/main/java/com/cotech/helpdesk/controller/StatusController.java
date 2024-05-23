package com.cotech.helpdesk.controller;


import com.cotech.helpdesk.UrlPrefix;
import com.cotech.helpdesk.model.Status;
import com.cotech.helpdesk.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = UrlPrefix.STATUS)
@RequiredArgsConstructor
public class StatusController {

    public final StatusService statusService;

    @GetMapping(value = "/all")
    public List<Status> getStatus(){
        return this.statusService.getAvailableStatuses();
    }
}
