package model;

import model.data.entities.History;

public interface MissesManager {
    void addUserHistory(String sessionId, History history);
    boolean hasTwoMissesInARow(String sessionId);
}
