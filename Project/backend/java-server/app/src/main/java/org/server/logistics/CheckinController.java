package com.server.logistics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckinController {

    @GetMapping("/api/v1/check_in")
    public String checkin() {
        return "Hello, world!";
    }
}