package com.example.demo.service;
import com.example.demo.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.repository.todorepo;

import java.util.List;

@Service
public class todoservice {
    @Autowired
    private todorepo todorepo;
    /* todoservice(){
         todorepo = new todorepo();
     }*/
    public Todo createtodo(Todo todo){
        return todorepo.save(todo);
    }
    public List<Todo> getTodosByEmail(String email) {
        return todorepo.findByEmail(email);
    }
    public Todo gettodobyid(Long id) {
        return todorepo.findById(id).orElse(null);
    }
public List<Todo> getAlltodos(){
    return todorepo.findAll();}
    //pagination
    public Page<Todo> getpagedtodo(int page, int size){
        Pageable p= PageRequest.of(page,size);
        return todorepo.findAll(p);
    }
    public void deletetodo(Long id) {
        todorepo.deleteById(id);
    }
    public  Todo updatetodo(Long id, Todo todo) {
        Todo exist = todorepo.findById(id).orElseThrow(() -> new RuntimeException("id not found for updtaion"));
        exist.setTitle(todo.getTitle());
        exist.setDescription(todo.getDescription());
        exist.setDescription(todo.getDescription());
        exist.setIsCompleted(todo.getIsCompleted());
        return todorepo.save(exist);
    }
}
