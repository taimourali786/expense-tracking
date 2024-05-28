package com.cotech.helpdesk.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer id;
    @NonNull
    private String name;
    private Integer leadId;
}
