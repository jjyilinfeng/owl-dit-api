package com.ylf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Master 2021/2/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dit {
    int ditId;
    String ditUserName;
    String ditMessage;
    int ditLike;
    String ditUuid;
    String ditDateTime;
}
