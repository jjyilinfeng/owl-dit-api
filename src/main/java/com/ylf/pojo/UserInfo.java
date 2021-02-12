package com.ylf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Master 2021/2/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private int userId;
    private String userName;
    private String userNickName;
    private String userGender;
    private String userAge;
    private String userBirthday;
    private String userSign;
    private String userFaceUuid;
}
