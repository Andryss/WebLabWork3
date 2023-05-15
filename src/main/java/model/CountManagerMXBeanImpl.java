package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.util.MBeanManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data

@ManagedBean(name = "countManager")
@ApplicationScoped
public class CountManagerMXBeanImpl implements CountManagerMXBean {

    @ManagedProperty("#{beanManager}")
    private MBeanManager beanManager;

    @PostConstruct
    public void init() {
        beanManager.registerMBean("counter", this);
    }

    @PreDestroy
    public void destroy() {
        beanManager.unregisterMBean(this);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class SessionInfo {
        long allCount = 0;
        long hitCount = 0;
        long missCount = 0;
    }

    private static final SessionInfo EMPTY_INFO = new SessionInfo();

    private final Map<String, SessionInfo> sessionInfoMap = new ConcurrentHashMap<>();

    @Override
    public void addUserResult(String sessionId, boolean result) {
        SessionInfo sessionInfo = sessionInfoMap.computeIfAbsent(sessionId, s -> new SessionInfo());

        sessionInfo.setAllCount(sessionInfo.getAllCount() + 1);
        if (result) sessionInfo.setHitCount(sessionInfo.getHitCount() + 1);
        else sessionInfo.setMissCount(sessionInfo.getMissCount() + 1);
    }

    @Override
    public long getAllCount(String sessionId) {
        return sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO).getAllCount();
    }

    @Override
    public long getMissedCount(String sessionId) {
        return sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO).getMissCount();
    }

    @Override
    public long getHitCount(String sessionId) {
        return sessionInfoMap.getOrDefault(sessionId, EMPTY_INFO).getHitCount();
    }
}
