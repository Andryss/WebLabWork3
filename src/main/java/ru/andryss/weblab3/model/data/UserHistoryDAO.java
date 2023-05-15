package ru.andryss.weblab3.model.data;

import ru.andryss.weblab3.model.data.entities.History;
import ru.andryss.weblab3.model.data.entities.User;

public interface UserHistoryDAO {
    User getUserById(String sessionId);
    void saveUser(User user);

    void saveHistory(History history);
    void deleteHistory(History history);
    void clearTables();
}
