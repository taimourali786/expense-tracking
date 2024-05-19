package com.cotech.expensetracking.jpa.userauth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Integer> {
}
