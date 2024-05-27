package com.cotech.helpdesk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @NonNull
    private String name;
    private Integer departmentId;
    private Integer parentCategoryId;
}
