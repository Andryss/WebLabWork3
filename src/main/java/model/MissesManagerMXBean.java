package model;

public interface MissesManagerMXBean {
    void addUserResult(String sessionId, boolean result);
    boolean hasTwoMissesInARow(String sessionId);
}
