package ru.andryss.weblab3.model;

import java.util.Set;

public interface MissesManagerMXBean {
    Set<String> getSessions();
    void addUserResult(String sessionId, boolean result);
    boolean hasTwoMissesInARow(String sessionId);
}
