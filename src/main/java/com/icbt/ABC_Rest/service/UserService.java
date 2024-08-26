package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.UserDto;
import com.icbt.ABC_Rest.entity.User;
import com.icbt.ABC_Rest.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto saveUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (userDto.getRole() == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        User user = userRepo.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return modelMapper.map(userList, new org.modelmapper.TypeToken<List<UserDto>>(){}.getType());
    }

    public UserDto updateUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (userDto.getRole() == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        User existingUser = userRepo.findByEmail(userDto.getEmail());
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found with email: " + userDto.getEmail());
        }

        // Update existing user details
        existingUser.setUsername(userDto.getUsername());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setAddress(userDto.getAddress());
        existingUser.setRole(userDto.getRole());

        User updatedUser = userRepo.save(existingUser);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    public UserDto loginUser(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return modelMapper.map(user, UserDto.class);
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    public boolean deleteUser(UserDto userDto) {
        if (userDto == null || userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        User user = userRepo.findByEmail(userDto.getEmail());
        if (user != null) {
            userRepo.delete(user);
            return true;
        }
        return false;
    }
}
