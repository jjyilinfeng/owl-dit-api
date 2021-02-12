package com.ylf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Master 2021/2/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userLoginIp;
    private String userType;
    private String userLoginTime;

    private String userIp;
}
