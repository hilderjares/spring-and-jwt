package com.hilderjares.spring.repository;

import com.hilderjares.spring.entity.UserDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDomain, Long> {

    UserDomain findByUsername(String username);
}
