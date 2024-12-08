package org.server;

public class OneAndTwoWayMatches {

    public boolean updateMatches(String discordId, String matchedId) {
        PostgreSQLController pgController = new PostgreSQLController();
        boolean exists = pgController.existsInOneWay(discordId, matchedId);
        if (exists) {
            boolean deleted = pgController.deleteOneWayMatched(discordId, matchedId);
            if (!deleted) {
                return false;
            }
            boolean added_to_two_way = pgController.updateTwoWayMatched(discordId, matchedId);
            if (!added_to_two_way) {
                return false;
            }
        } else {
            boolean added = pgController.updateOneWayMatched(discordId, matchedId);
            if (!added) {
                return false;
            }
        }
        return true;
    }
}
