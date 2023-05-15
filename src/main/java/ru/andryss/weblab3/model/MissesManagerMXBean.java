package ru.andryss.weblab3.model;

public interface MissesManagerMXBean {
    void addUserResult(String sessionId, boolean result);
    boolean hasTwoMissesInARow(String sessionId);
}
