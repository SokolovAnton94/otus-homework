package ru.otus.homework.anton.sokolov;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Monitor {
    private final Map<String, Boolean> threadsCondition = new ConcurrentHashMap<>();

    public boolean canStart(String threadName) {
        return threadsCondition.get(threadName);
    }

    public void addThread(String threadName) {
        threadsCondition.put(threadName, true);
    }

    public void wasStarted(String threadName) {
        threadsCondition.put(threadName, false);
        Collection<Boolean> values = threadsCondition.values();
        if (!values.contains(true)) {
            threadsCondition.replaceAll((k, v) -> true);
        }
        threadsCondition.put(threadName, false);
    }
}
