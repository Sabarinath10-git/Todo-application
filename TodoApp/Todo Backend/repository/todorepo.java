package com.example.demo.repository;

import com.example.demo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface todorepo extends JpaRepository<Todo,Long> {
    List<Todo> findByEmail(String email);
    }

