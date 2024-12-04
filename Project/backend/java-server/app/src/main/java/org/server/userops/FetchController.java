package org.server.userops;

import com.google.gson.JsonObject;
import org.server.DBController;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the controller for the fetch_by_uuid and fetch_by_discordid routes which return the user object in JsonObject.
 *
 * @author adhit2
 */
@RestController
public class FetchController {

  @Async
  @GetMapping(path = "/api/v1/fetch_by_uuid/{uuid}")
  public ResponseEntity<JsonObject> fetch_by_uuid(@PathVariable String uuid) {
    DBController dbController = new DBController("main.db");
    try {
      return ResponseEntity.ok(
              dbController.getUser(uuid, false).toJson()
      );
    } catch (NullPointerException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Async
  @GetMapping(path = "/api/v1/fetch_by_discordid/{discordid}")
  public ResponseEntity<JsonObject> fetch_by_discordid(
          @PathVariable String discordid
  ) {
    DBController dbController = new DBController("main.db");
    try {
      return ResponseEntity.ok(
              dbController.getUser(discordid, true).toJson()
      );
    } catch (NullPointerException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
