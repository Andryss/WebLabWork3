package model;

import java.util.List;

public interface HistoryManager {
    Response addUserRequest(Object user, Request request);
    List<Response> getUserHistory(Object user);

    HistoryManager instance = new HistoryManagerImpl();
}
