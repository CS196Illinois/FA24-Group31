package org.server.logistics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the controller for the check_in route which returns the String "OK".
 * @author adhit2
 */
@RestController
public class CheckinController {

    /**
     * Returns OK to make sure that the server is up.
     */
    @GetMapping("/api/v1/check_in")
    public String check_in() {
        return "OK";
    }
}
