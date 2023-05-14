package model;

import model.data.entities.History;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.*;

@ManagedBean(name = "countManager")
@ApplicationScoped
public class CountManagerImpl implements CountManager {

    private static class SessionInfo {
        long allCount = 0;
        long hitCount = 0;
        long missCount = 0;
        final LinkedList<History> lastAdded = new LinkedList<>();
    }

    private static final SessionInfo EMPTY_INFO = new SessionInfo();

    private final Map<String, SessionInfo> sessionInfoMap = new HashMap<>();

    @Override
    public void addUserHistory(String sessionId, History history) {
        SessionInfo sessionInfo = sessionInfoMap.computeIfAbsent(sessionId, s -> new SessionInfo());

        sessionInfo.allCount++;
        if (history.isResult()) sessionInfo.hitCount++;
        else sessionInfo.missCount++;

        LinkedList<History> histories = sessionInfo.lastAdded;
        histories.addLast(history);
        while (histories.size() > 2) histories.removeFirst();
    }

    @Override
    public long getAllCount(String sessionId) {
        return sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO).allCount;
    }

    @Override
    public long getMissedCount(String sessionId) {
        return sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO).missCount;
    }

    @Override
    public long getHitCount(String sessionId) {
        return sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO).hitCount;
    }

    @Override
    public boolean hasTwoMissesInARow(String sessionId) {
        LinkedList<History> histories = sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO).lastAdded;
        if (histories.size() < 2) return false;
        Iterator<History> iterator = histories.descendingIterator();
        return !iterator.next().isResult() && !iterator.next().isResult();
    }

    @Override
    public double getMissesPercentage(String sessionId) {
        SessionInfo sessionInfo = sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO);
        if (sessionInfo.allCount == 0) return 0.0;
        return (double) sessionInfo.missCount / sessionInfo.allCount;
    }

    @Override
    public double getHitPercentage(String sessionId) {
        SessionInfo sessionInfo = sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO);
        if (sessionInfo.allCount == 0) return 0.0;
        return (double) sessionInfo.hitCount / sessionInfo.allCount;
    }
}
