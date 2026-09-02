package com.example.demo.repository;

import com.example.demo.models.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  userrepo extends JpaRepository<user,Long> {
    Optional<user> findByEmail(String email);
}
