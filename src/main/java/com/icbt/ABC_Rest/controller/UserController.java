package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.UserDto;
import com.icbt.ABC_Rest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/User")
@CrossOrigin(origins = "http://127.0.0.1:5501")  // Ensure this matches the frontend origin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getUsers")
    public List<UserDto> getUser(){
        return userService.getAllUsers();
    }

    @PostMapping(value = "/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto){
        try {
            UserDto savedUser = userService.saveUser(userDto);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto){
        try {
            UserDto updatedUser = userService.updateUser(userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteUser/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        try {
            boolean isDeleted = userService.deleteUserByEmail(email);
            if (isDeleted) {
                return ResponseEntity.ok("User deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @PostMapping(value = "/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        try {
            UserDto user = userService.loginUser(email, password);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @GetMapping(value = "/currentUser")
    public ResponseEntity<UserDto> getCurrentUser(HttpServletRequest request) {
        // Logic to determine the current user. This could be based on a session, JWT token, etc.
        // For demonstration, returning a hardcoded user. Replace with actual logic.
        String currentUserEmail = "user@example.com"; // This should be determined from the session or token
        UserDto currentUser = userService.getUserByEmail(currentUserEmail);

        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
