package com.cotech.helpdesk.jpa.status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    Optional<StatusEntity> findByName(String name);
}
