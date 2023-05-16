package ru.andryss.weblab3.model;

import java.util.Set;

public interface CountManagerMXBean {
    Set<String> getSessions();
    int getSessionsCount();
    void addUserResult(String sessionId, boolean result);
    long getAllCount(String sessionId);
    long getMissedCount(String sessionId);
    long getHitCount(String sessionId);
}
