package ru.andryss.weblab3.model;

import lombok.Setter;
import ru.andryss.weblab3.model.util.MBeanManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ManagedBean(name = "missesManager")
@ApplicationScoped
public class MissesManagerMXBeanImpl implements MissesManagerMXBean {

    @Setter
    @ManagedProperty("#{beanManager}")
    private MBeanManager beanManager;

    private static final LinkedList<Boolean> EMPTY_HISTORY = new LinkedList<>();

    private final Map<String, LinkedList<Boolean>> historyMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        beanManager.registerMBean("misser", this);
    }

    @PreDestroy
    public void destroy() {
        beanManager.unregisterMBean(this);
    }

    @Override
    public Set<String> getSessions() {
        return historyMap.keySet();
    }

    @Override
    public void addUserResult(String sessionId, boolean result) {
        LinkedList<Boolean> histories = historyMap.computeIfAbsent(sessionId, s -> new LinkedList<>());
        histories.addLast(result);
        while (histories.size() > 2) histories.removeFirst();
    }

    @Override
    public boolean hasTwoMissesInARow(String sessionId) {
        LinkedList<Boolean> histories = historyMap.getOrDefault(sessionId, EMPTY_HISTORY);
        if (histories.size() < 2) return false;
        Iterator<Boolean> iterator = histories.descendingIterator();
        return !iterator.next() && !iterator.next();
    }
}
