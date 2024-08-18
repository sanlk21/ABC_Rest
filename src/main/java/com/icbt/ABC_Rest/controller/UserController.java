package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.UserDto;
import com.icbt.ABC_Rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/User")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getUsers")
    public List<UserDto> getUser(){
        return userService.getAllUsers();
    }
    @PostMapping(value = "/saveUser")
    public UserDto saveUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto);
    }
    @PutMapping(value = "/updateUser")
    public String updateUser(){
        return "update ABC";
    }
    @DeleteMapping(value = "/deleteUser")
    public String deleteUser(){
        return "delete ABC";
    }

}
