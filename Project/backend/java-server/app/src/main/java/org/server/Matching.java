package org.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Matching {
  private List<User> matchList = new ArrayList<>();
  private String sessionToken;

  public Matching(String sessionToken) {
    this.sessionToken = sessionToken;
  }

  public void newFillMatchList() {
    matchList.clear();
    PostgreSQLController pgController = new PostgreSQLController();
    try {
      matchList = pgController.filterWithSQL(sessionToken);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public List<User> getMatchList() {
    return matchList;
  }
}
