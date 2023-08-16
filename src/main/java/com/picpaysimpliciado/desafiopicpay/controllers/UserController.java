package com.picpaysimpliciado.desafiopicpay.controllers;

import com.picpaysimpliciado.desafiopicpay.domain.user.User;
import com.picpaysimpliciado.desafiopicpay.dtos.UserDTO;
import com.picpaysimpliciado.desafiopicpay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
        }
}

