package com.ylf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Master 2021/2/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIndex {
    int indexId;
    String indexUserName;
    int indexVisit;
    String indexBGIUuid;
}
