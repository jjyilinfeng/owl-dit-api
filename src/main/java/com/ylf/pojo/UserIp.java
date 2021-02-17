package com.ylf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Master 2021/2/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIp {
    private int id;
    private String userNameForIp;
    private String userIp;
}
