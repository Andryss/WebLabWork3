package ru.andryss.weblab3.model;

public interface CountManagerMXBean {
    void addUserResult(String sessionId, boolean result);
    long getAllCount(String sessionId);
    long getMissedCount(String sessionId);
    long getHitCount(String sessionId);
}
