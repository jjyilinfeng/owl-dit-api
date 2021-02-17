package com.ylf.controller;

import com.ylf.util.Functions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Master 2021/2/12
 */
@RestController
@RequestMapping("/util")
public class UtilController {

    @RequestMapping("/getUuid")
    public String getUuid(){
        return Functions.getUuid();
    }
}
