package com.example.demo.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class todo2 {
    @Id
            @GeneratedValue
    int id;
    String name;
}
