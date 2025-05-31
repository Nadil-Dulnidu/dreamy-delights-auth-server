package com.dreamy_delights.root.mapper;

import com.dreamy_delights.root.common.Constants;
import com.dreamy_delights.root.dto.Admin;
import com.dreamy_delights.root.dto.RegularUser;
import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.entity.UserEntity;

import java.util.Objects;

public class UserDTOEntityMapper {
    public static UserEntity map(User user){
        UserEntity userEntity = new UserEntity();
        if(user instanceof Admin admin){
            userEntity.setUsername(admin.getUsername());
            userEntity.setPassword(admin.getPassword());
            userEntity.setEmail(admin.getEmail());
            userEntity.setMobile(admin.getPhone());
            userEntity.setRoleId(admin.getRole());
            return userEntity;
        }else if(user instanceof RegularUser regularUser){
            userEntity.setUsername(regularUser.getUsername());
            userEntity.setPassword(regularUser.getPassword());
            userEntity.setEmail(regularUser.getEmail());
            userEntity.setMobile(regularUser.getPhone());
            userEntity.setRoleId(regularUser.getRole());
            return userEntity;
        }
        return null;
    }

    public static User map(UserEntity userEntity){
        if(Objects.equals(userEntity.getRoleId(), Constants.ROLE_ADMIN)){
            Admin admin = new Admin();
            admin.setId(userEntity.getId());
            admin.setUsername(userEntity.getUsername());
            admin.setPassword(userEntity.getPassword());
            admin.setEmail(userEntity.getEmail());
            admin.setPhone(userEntity.getMobile());
            admin.setRole(userEntity.getRoleId());
            return admin;
        }else if(Objects.equals(userEntity.getRoleId(), Constants.ROLE_USER)){
            RegularUser regularUser = new RegularUser();
            regularUser.setId(userEntity.getId());
            regularUser.setUsername(userEntity.getUsername());
            regularUser.setPassword(userEntity.getPassword());
            regularUser.setEmail(userEntity.getEmail());
            regularUser.setPhone(userEntity.getMobile());
            regularUser.setRole(userEntity.getRoleId());
            return regularUser;
        }
        return null;
    }
}
