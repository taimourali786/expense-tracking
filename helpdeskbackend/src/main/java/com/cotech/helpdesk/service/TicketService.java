package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.ticket.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CategoryService categoryService;
    private final DepartmentService departmentService;
    private final PriorityService priorityService;
    private final StatusService statusService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TicketService(final TicketRepository ticketRepository,
                         final CategoryService categoryService,
                         final DepartmentService departmentService,
                         final PriorityService priorityService,
                         final StatusService statusService,
                         final UserService userService,
                         final ModelMapper mapper) {
        this.ticketRepository = ticketRepository;
        this.categoryService = categoryService;
        this.departmentService = departmentService;
        this.priorityService = priorityService;
        this.statusService = statusService;
        this.userService = userService;
        this.modelMapper = mapper;


    }


}
