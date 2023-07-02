package ru.otus.homework.anton.sokolov.test;

import ru.otus.homework.anton.sokolov.logger.Log;

public class TestLoggingImpl implements TestLogging{

    public void calculation(int param) {}

    @Log
    public void calculation(int param1, int param2){}

    @Log
    public void calculation(int param1, int param2, String param3){}
}
