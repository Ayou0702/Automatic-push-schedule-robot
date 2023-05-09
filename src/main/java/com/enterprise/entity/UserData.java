package com.enterprise.entity;

import lombok.Data;

@Data
public class UserData {

    public int userId;
    public String userName,userEmail;
    public byte[] userAvatar;

}
