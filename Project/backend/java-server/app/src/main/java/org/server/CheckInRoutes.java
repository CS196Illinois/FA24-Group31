package org.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** This class represents the routes for check-in. */
@RestController
public class CheckInRoutes {

  /**
   * Checks in the user.
   *
   * @return the response entity containing whether the check-in was successful
   */
  @GetMapping("/api/v1/checkin")
  public String checkIn() {
    return "Check-in successful!";
  }
}
