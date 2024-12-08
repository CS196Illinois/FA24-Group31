package org.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Matching {
    private List<User> matchList = new ArrayList<>();

    public boolean fillMatchList(){
        matchList.clear();
        PostgreSQLController pgController = new PostgreSQLController();
        try {
            matchList = pgController.getUsers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<User> getMatchList() {return matchList;}

    public boolean filterMatches(int minAge, int maxAge, String[] ranks, String[] roles) {
        List<User> newMatchList = new ArrayList<>();
        fillMatchList();
        matchList.forEach((user) -> {
            if (user.getPrivateUser().getAge() >= minAge && user.getPrivateUser().getAge() <= maxAge) {
                String[] userRoles = user.getPublicUser().getRoles();
                boolean foundRole = false;
                outerLoop:
                for (int i = 0; i < userRoles.length; i++) {
                    String currentRole = userRoles[i];
                    for (int j = 0; j < roles.length; j++) {
                        if (currentRole.toLowerCase().equals(roles[j].toLowerCase())) {
                            foundRole = true;
                            break outerLoop;
                        }
                    }
                }
                if (foundRole) {
                    boolean foundRank = false;
                    String userRole = user.getPublicUser().getRank();
                    for (int i = 0; i < ranks.length; i++) {
                        if (userRole.toLowerCase().equals(ranks[i].toLowerCase())) {
                            foundRank = true;
                            break;
                        }
                    }
                    if (foundRank) {
                        newMatchList.add(user);
                    }
                }
            }
        });
        matchList = newMatchList;
        if (matchList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
