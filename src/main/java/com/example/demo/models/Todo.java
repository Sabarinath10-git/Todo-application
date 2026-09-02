package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data// for getters and setters
public class Todo {
    @Id//for id
            @GeneratedValue //auto generate of id
    Long id;
    @NotNull
    @NotBlank
    String title;
    String description;
   Boolean isCompleted;
   @Email
String email;
}
