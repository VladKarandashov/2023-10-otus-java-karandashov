package ru.otus.testframework.util;

import ru.otus.testframework.enumerate.Annotations;
import ru.otus.testframework.exception.TestInvokeException;

import java.lang.reflect.Method;
import java.util.List;

public class ReflectionTestUtil {

    public static void invokeMethod(List<Method> methodList, Object instance) {
        if (methodList.isEmpty()) return;
        methodList.forEach(method -> {
            try {
                method.invoke(instance);
            } catch (Exception e) {
                System.out.println("Method " + method.getName() + " threw exception: " + e.getCause() + ": " + e.getMessage());
            }
        });
    }

    public static void invokeMethod(Method method, Object instance) {
        try {
            method.invoke(instance);
        } catch (Exception e) {
            System.out.println("Method " + method.getName() + " threw exception: " + e.getCause() + ": " + e.getMessage());
            throw new TestInvokeException(e.getMessage());
        }
    }

    public static List<Method> findAnnotatedMethods(List<Method> methods, Annotations annotation) {
        return methods.stream()
                .filter(testClassMethod -> testClassMethod.isAnnotationPresent(annotation.getAnnotationClass()))
                .toList();
    }

}