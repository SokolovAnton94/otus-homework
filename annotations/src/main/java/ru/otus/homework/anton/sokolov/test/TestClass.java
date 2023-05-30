package ru.otus.homework.anton.sokolov.test;

import ru.otus.homework.anton.sokolov.annotation.After;
import ru.otus.homework.anton.sokolov.annotation.Before;
import ru.otus.homework.anton.sokolov.annotation.Test;

public class TestClass {

    @Before
    public void init() {
        System.out.println("Inside a method with an annotation \"Before\"");
    }

    @Test
    public void successTest() {
        System.out.println("Inside a \"successTest\" method");
    }

    @Test
    private void privateTest() {}

    @Test
    public void runtimeExceptionTest() {
        throw new RuntimeException("\"Exception in \"runtimeExceptionTest\" method");
    }

    @After
    public void destroy() {
        System.out.println("Inside a method with an annotation \"After\"");
    }

}
