package com.cotech.helpdesk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    private Long id;
    private String title;
    private String description;
    private Instant createdDate;
    private Integer reporterId;
    private Integer assigneeId;
    private Integer statusId;
    private Integer categoryId;
    private Integer priorityId;

}
