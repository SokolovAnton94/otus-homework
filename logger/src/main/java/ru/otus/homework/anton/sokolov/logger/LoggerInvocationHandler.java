package ru.otus.homework.anton.sokolov.logger;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;



@Slf4j
public class LoggerInvocationHandler implements InvocationHandler {

    private final Object target;
    private final static String LOG_MESSAGE = "Executed method: {}, param: {}";

    public LoggerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (isAnnotatedMethod(method)) {
            log.info(LOG_MESSAGE, method.getName(), Arrays.toString(args));
        }
        return method.invoke(target, args);
    }

    private boolean isAnnotatedMethod(Method method) {
        try {
            Method declaredMethod = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            return declaredMethod.isAnnotationPresent(Log.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
