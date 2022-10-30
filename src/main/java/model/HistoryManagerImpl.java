package model;

import model.data.UserHistoryDAO;
import model.data.entities.History;
import model.data.entities.User;

import java.util.*;

public class HistoryManagerImpl implements HistoryManager {
    private static final int maxUserLength = 10;

    private final UserHistoryDAO dao = UserHistoryDAO.instance;


    @Override
    public void addUserRequest(String sessionId, Request request) {
        User user = dao.getUserById(sessionId);
        if (user == null) {
            user = new User(sessionId);
            dao.saveUser(user);
        }
        History newHistory = createResponse(request);
        newHistory.setUser(user);
        dao.saveHistory(newHistory);

        user = dao.getUserById(sessionId);
        SortedSet<History> history = user.getHistories();
        System.out.println(history.size());
        while (history.size() > maxUserLength) {
            History lastHistory = history.last();
            dao.deleteHistory(lastHistory);
            history.remove(lastHistory);
        }
    }

    private History createResponse(Request request) {
        long startTime = System.currentTimeMillis();
        boolean result = AreaChecker.instance.check(request);
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
        User user = dao.getUserById(sessionId);
        if (user == null) {
            user = new User(sessionId);
            dao.saveUser(user);
        }
        return user.getHistories();
    }
}
