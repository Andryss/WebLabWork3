package ru.andryss.weblab3.model;

import ru.andryss.weblab3.model.data.entities.History;

import java.util.SortedSet;

public interface HistoryManager {
    void addUserRequest(String sessionId, Request request);
    SortedSet<History> getUserHistory(String sessionId);
}
