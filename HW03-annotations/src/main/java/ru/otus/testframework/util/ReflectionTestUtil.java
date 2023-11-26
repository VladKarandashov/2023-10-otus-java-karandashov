package ru.otus.testframework.util;

import ru.otus.testframework.enumerate.Annotations;
import ru.otus.testframework.exception.TestInvokeException;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Вспомогательный утилитный класс для тестового фреймворка
 */
public class ReflectionTestUtil {

    /**
     * Метод для исполнения методов с "погашением" всех исключений
     *
     * @param methodList - список методов
     */
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

    /**
     * Метод для исполнения тестовых методов, который все исключения превращает в {@link TestInvokeException}
     *
     * @param method - метод для исполнения
     */
    public static void invokeMethod(Method method, Object instance) {
        try {
            method.invoke(instance);
        } catch (Exception e) {
            System.out.println("Method " + method.getName() + " threw exception: " + e.getCause() + ": " + e.getMessage());
            throw new TestInvokeException(e.getMessage());
        }
    }

    /**
     * Принимает список методов класса и группирует их по группам тестирования
     *
     * @param classMethods - список методов
     * @return Map, где ключ - это аннотация тестового фреймворка, а значение - список методов отмеченных данной тестовой аннотацией
     */
    public static Map<Annotations, List<Method>> getAnnotatedMethods(List<Method> classMethods) {
        List<Annotations> annotationsList = List.of(Annotations.values());
        Map<Annotations, List<Method>> methodMap = new LinkedHashMap<>();

        annotationsList.forEach(annotation ->
                methodMap.put(annotation, findAnnotatedMethods(classMethods, annotation))
        );
        return methodMap;
    }

    /**
     * Принимает список методов класса и фильтрует их, оставляя только методы, имеющие отношение к тестам
     *
     * @param methods - список методов
     * @param annotation - аннотации для фильтрации
     * @return отфильтрованный список методов
     */
    public static List<Method> findAnnotatedMethods(List<Method> methods, Annotations annotation) {
        return methods.stream()
                .filter(testClassMethod -> testClassMethod.isAnnotationPresent(annotation.getAnnotationClass()))
                .toList();
    }
}