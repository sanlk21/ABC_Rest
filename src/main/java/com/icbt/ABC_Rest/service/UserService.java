package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.UserDto;
import com.icbt.ABC_Rest.entity.User;
import com.icbt.ABC_Rest.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    public UserDto saveUser(UserDto userDto){
        userRepo.save(modelMapper.map(userDto, User.class));
        return userDto;
    }
    public List<UserDto> getAllUsers(){
        List<User>userList=userRepo.findAll();
        return modelMapper.map(userList,new TypeToken<List<UserDto>>(){}.getType());
    }
}
