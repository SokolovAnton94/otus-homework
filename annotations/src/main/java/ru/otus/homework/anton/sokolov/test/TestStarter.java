package ru.otus.homework.anton.sokolov.test;

import ru.otus.homework.anton.sokolov.annotation.After;
import ru.otus.homework.anton.sokolov.annotation.Before;
import ru.otus.homework.anton.sokolov.annotation.Test;
import ru.otus.homework.anton.sokolov.exception.ExceptionDictionary;
import ru.otus.homework.anton.sokolov.exception.TestException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestStarter {

    private static final String RESULT = "TESTS" + System.lineSeparator() + "Total: %s" + System.lineSeparator() + "Success: %s" + System.lineSeparator() + "Unsuccess: %s" + System.lineSeparator();
    private static final String DELIMITER = "================================";
    private static final String UNSUCCESSFUL_METHOD_RESULT = "Method: %s" + System.lineSeparator() + "Error: %s" + System.lineSeparator();

    public void testIt(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            List<Method> beforeMethods = getAnnotateMethods(clazz, Before.class);
            List<Method> testMethods = getAnnotateMethods(clazz, Test.class);
            List<Method> afterMethods = getAnnotateMethods(clazz, After.class);

            int successfulMethodsCount = 0;
            int unsuccessfulMethodsCount = 0;
            Map<String, String> unsuccessfulMethodResults = new HashMap<>();
            for (Method testMethod : testMethods) {
                Object testClassInstance = getInstanceOfClass(clazz);
                invokeMethods(beforeMethods, testClassInstance);

                try {
                    testMethod.invoke(testClassInstance);
                    successfulMethodsCount += 1;
                } catch (InvocationTargetException | IllegalAccessException e) {
                    unsuccessfulMethodsCount += 1;
                    saveError(unsuccessfulMethodResults, testMethod.getName(), e);
                    String errorMessage;
                    if (e instanceof InvocationTargetException) {
                        errorMessage = e.getCause().toString();
                    } else {
                        errorMessage = e.toString();
                    }
                    unsuccessfulMethodResults.put(testMethod.getName(), errorMessage);
                }

                invokeMethods(afterMethods, testClassInstance);
            }
            printResult(successfulMethodsCount, unsuccessfulMethodsCount, unsuccessfulMethodResults);
        } catch (ClassNotFoundException e) {
            System.out.println(ExceptionDictionary.TEST_CLASS_NOT_FOUND);
        } catch (TestException e) {
            System.out.printf((ExceptionDictionary.TESTING_ABORTED.getMessage()), e.getMessage());
        }
    }

    private List<Method> getAnnotateMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> result = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }

    private Object getInstanceOfClass(Class<?> clazz) throws TestException {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new TestException(ExceptionDictionary.CREATE_TEST_CLASS_ERROR.getMessage());
        }
    }

    private void invoke(Method method, Object testClassInstance) throws TestException {
        try {
            if (!method.canAccess(testClassInstance)) {
                method.setAccessible(true);
            }
            method.invoke(testClassInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new TestException(String.format(ExceptionDictionary.CALL_METHOD_ERROR.getMessage(), e, method.getName()));
        }
    }

    private void invokeMethods(List<Method> methods, Object testClassInstance) throws TestException {
        for (Method method : methods) {
            invoke(method, testClassInstance);
        }
    }

    private void printResult(int successfulMethodsCount, int unsuccessfulMethodsCount, Map<String, String> unsuccessfulMethodResults) {
        System.out.printf((RESULT), successfulMethodsCount + unsuccessfulMethodsCount, successfulMethodsCount, unsuccessfulMethodsCount);

        for (Map.Entry<String, String> entry : unsuccessfulMethodResults.entrySet()) {
            System.out.println(DELIMITER);
            System.out.printf((UNSUCCESSFUL_METHOD_RESULT), entry.getKey(), entry.getValue());
            System.out.println(DELIMITER);
        }
    }

    private void saveError(Map<String, String> unsuccessfulMethodResults, String testMethodName, Exception e) {
        String errorMessage;
        if (e instanceof InvocationTargetException) {
            errorMessage = e.getCause().toString();
        } else {
            errorMessage = e.toString();
        }
        unsuccessfulMethodResults.put(testMethodName, errorMessage);
    }
}
