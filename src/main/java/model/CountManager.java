package model;

import model.data.entities.History;

public interface CountManager {
    void addUserHistory(String sessionId, History history);
    long getAllCount(String sessionId);
    long getMissedCount(String sessionId);
    long getHitCount(String sessionId);
}
