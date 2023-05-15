package model;

import model.data.UserHistoryDAO;
import model.data.entities.History;
import model.data.entities.User;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.SortedSet;

@ManagedBean(name = "historyManager")
@ApplicationScoped
public class HistoryManagerImpl implements HistoryManager {
    private static final int maxUserLength = 10;

    @ManagedProperty("#{userHistoryDAO}")
    private UserHistoryDAO dao;
    public void setDao(UserHistoryDAO dao) {
        this.dao = dao;
    }

    @ManagedProperty("#{areaChecker}")
    private AreaChecker areaChecker;
    public void setAreaChecker(AreaChecker areaChecker) {
        this.areaChecker = areaChecker;
    }

    @ManagedProperty("#{countManager}")
    private CountManager countManager;
    public void setCountManager(CountManager countManager) {
        this.countManager = countManager;
    }

    @ManagedProperty("#{missesManager}")
    private MissesManager missesManager;
    public void setMissesManager(MissesManager missesManager) {
        this.missesManager = missesManager;
    }

    @Override
    public void addUserRequest(String sessionId, Request request) {
        User user = getUserById(sessionId);
        History newHistory = createResponse(request);
        newHistory.setUser(user);
        dao.saveHistory(newHistory);

        countManager.addUserHistory(sessionId, newHistory);
        missesManager.addUserHistory(sessionId, newHistory);

        user = dao.getUserById(sessionId);
        SortedSet<History> history = user.getHistories();
        while (history.size() > maxUserLength) {
            History lastHistory = history.last();
            dao.deleteHistory(lastHistory);
            history.remove(lastHistory);
        }
    }

    private History createResponse(Request request) {
        long startTime = System.currentTimeMillis();
        boolean result = areaChecker.check(request);
        long finishTime = System.currentTimeMillis();
        return new History(
                startTime,
                finishTime - startTime,
                request.getX(),
                request.getY(),
                request.getR(),
                result
        );
    }

    @Override
    public SortedSet<History> getUserHistory(String sessionId) {
        return getUserById(sessionId).getHistories();
    }

    private User getUserById(String sessionId) {
        User user = dao.getUserById(sessionId);
        if (user == null) {
            user = new User(sessionId);
            dao.saveUser(user);
        }
        return user;
    }
}
