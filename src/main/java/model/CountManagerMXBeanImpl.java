package model;

import model.util.MBeanManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ManagedBean(name = "countManager")
@ApplicationScoped
public class CountManagerMXBeanImpl implements CountManagerMXBean {

    private static class SessionInfo {
        long allCount = 0;
        long hitCount = 0;
        long missCount = 0;
    }

    private static final SessionInfo EMPTY_INFO = new SessionInfo();

    private final Map<String, SessionInfo> sessionInfoMap = new ConcurrentHashMap<>();

    public Map<String, SessionInfo> getSessionInfoMap() {
        return sessionInfoMap;
    }

    @Override
    public void addUserResult(String sessionId, boolean result) {
        SessionInfo sessionInfo = sessionInfoMap.computeIfAbsent(sessionId, s -> new SessionInfo());

        sessionInfo.allCount++;
        if (result) sessionInfo.hitCount++;
        else sessionInfo.missCount++;
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


    @ManagedProperty("#{mBeanManager}")
    private MBeanManager mBeanManager;
    public void setmBeanManager(MBeanManager mBeanManager) {
        this.mBeanManager = mBeanManager;
    }

    @PostConstruct
    public void init() {
        mBeanManager.registerMBean("counter", this);
    }

    @PreDestroy
    public void destroy() {
        mBeanManager.unregisterMBean(this);
    }
}
