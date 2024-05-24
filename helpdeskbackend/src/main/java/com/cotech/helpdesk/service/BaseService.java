package com.cotech.helpdesk.service;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {

    @Getter
    private ModelMapper mapper;

}
