package model;

import model.data.entities.History;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

@ManagedBean(name = "missesManager")
@ApplicationScoped
public class MissesManagerImpl implements MissesManager {

    private final Map<String, LinkedList<History>> historyMap = new HashMap<>();

    private static final LinkedList<History> EMPTY_HISTORY = new LinkedList<>();

    @Override
    public void addUserHistory(String sessionId, History history) {
        LinkedList<History> histories = historyMap.computeIfAbsent(sessionId, s -> new LinkedList<>());
        histories.addLast(history);
        while (histories.size() > 2) histories.removeFirst();
    }

    @Override
    public boolean hasTwoMissesInARow(String sessionId) {
        LinkedList<History> histories = historyMap.getOrDefault(sessionId, EMPTY_HISTORY);
        if (histories.size() < 2) return false;
        Iterator<History> iterator = histories.descendingIterator();
        return !iterator.next().isResult() && !iterator.next().isResult();
    }
}
