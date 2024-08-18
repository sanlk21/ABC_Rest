package com.icbt.ABC_Rest.dto;

import com.icbt.ABC_Rest.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String address;
    private String email;
    private User.Role role;

}
