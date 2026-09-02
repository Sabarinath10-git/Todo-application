package com.example.demo.controller;

import com.example.demo.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.todoservice;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class todocontroller {
    @Autowired
    private todoservice todoservice;
    @PostMapping("/create")
    public ResponseEntity<Todo> createuser(@RequestBody Todo todo) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        todo.setEmail(email);

        return new ResponseEntity<>(
                todoservice.createtodo(todo),
                HttpStatus.CREATED
        );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @RequestBody Todo todo) {

        Todo updatedTodo = todoservice.updatetodo(id, todo);

        if (updatedTodo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
    //using getRefferenceById()
    @GetMapping("/get/{id}")
    public ResponseEntity<Todo> gettodobyid(@PathVariable Long id) {

        Todo todo = todoservice.gettodobyid(id);

        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Todo>> getalltodo() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return new ResponseEntity<>(
                todoservice.getTodosByEmail(email),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {

        todoservice.deletetodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
