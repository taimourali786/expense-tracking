package com.cotech.helpdesk.jpa.priority;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriorityRepository extends JpaRepository<PriorityEntity, Integer> {

    Optional<PriorityEntity> findByName(String  name);
}
