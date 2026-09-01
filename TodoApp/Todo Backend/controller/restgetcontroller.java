package com.example.demo.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
@RestController()
public class restgetcontroller {
  @GetMapping("/hello")
    String print(){
      return "Hello hi!!!!";
  }


}
