package model;

import java.util.*;

public class HistoryManagerImpl implements HistoryManager {
    private static final String mapName = "histories";
    private static final int maxUserLength = 10;

    private final Map<Object, LinkedList<Response>> histories = Collections.synchronizedMap(new WeakHashMap<>());

    @Override
    public Response addUserRequest(Object user, Request request) {
        LinkedList<Response> responses = getUserHistory0(user);
        responses.addFirst(createResponse(request));
        while (responses.size() > maxUserLength) responses.removeLast();
        return responses.getFirst();
    }

    private Response createResponse(Request request) {
        long startTime = System.currentTimeMillis();
        boolean result = AreaChecker.instance.check(request);
        long finishTime = System.currentTimeMillis();
        return new Response(finishTime - startTime, request, result);
    }

    @Override
    public List<Response> getUserHistory(Object user) {
        return getUserHistory0(user);
    }

    private LinkedList<Response> getUserHistory0(Object user) {
        return histories.computeIfAbsent(user, (u) -> new LinkedList<>());
    }
}
