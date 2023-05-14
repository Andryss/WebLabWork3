package model;

import model.data.entities.History;

import java.util.SortedSet;

public interface HistoryManager {
    void addUserRequest(String sessionId, Request request);
    SortedSet<History> getUserHistory(String sessionId);
}
