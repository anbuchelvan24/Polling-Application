package com.sap.polls;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class Testing {
    @PostMapping("/test")
    public void getfromurl(@RequestBody Map<String,Object> data){
        NewPolls newpoll = new NewPolls();
        newpoll.createPoll(data);
    }
}