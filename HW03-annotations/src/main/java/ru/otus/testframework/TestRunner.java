package ru.otus.testframework;

import ru.otus.testframework.enumerate.Annotations;
import ru.otus.testframework.exception.TestInvokeException;
import ru.otus.testframework.util.ReflectionTestUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Класс для запуска тестов
 */
public class TestRunner {

  /**
   * Запуск тестового класса по его имени
   *
   * @param className - полное имя класса со всеми пакетами
   * @throws ClassNotFoundException - если класс не найден
   */
  public void run(String className) throws ClassNotFoundException {
    Class<?> testClass = Class.forName(className);
    run(testClass);
  }

  /**
   * Запуск тестового класса
   *
   * @param testClass - тестовый класс
   */
  public void run(Class<?> testClass) {

    System.out.println("______________________________________");
    System.out.printf("ВЫПОЛНЯЮ ТЕСТЫ В КЛАССЕ  %s \n", testClass.getName());
    List<Method> classMethods = List.of(testClass.getMethods());
    Map<Annotations, List<Method>> annotatedMethodsMap = ReflectionTestUtil.getAnnotatedMethods(classMethods);

    try {
      int testsWithExceptionCount = startTests(testClass, annotatedMethodsMap);
      testStatistics(annotatedMethodsMap, testsWithExceptionCount);
    } catch (Exception e) {
      System.out.printf("Не получилось создать тестовый класс  %s \n", testClass.getName());
      testStatistics(annotatedMethodsMap, annotatedMethodsMap.get(Annotations.TEST).size());
    }
  }

  private int startTests(Class<?> testClass, Map<Annotations, List<Method>> annotationsEnumListMap)
          throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    int testsWithExceptionCount = 0;
    for (Method testMethod : annotationsEnumListMap.get(Annotations.TEST)) {
      System.out.printf("-> выполняю тест %s \n", testMethod.getName());
      var testClassInstance = testClass.getConstructor().newInstance();
      System.out.printf("-> -> Создал новый экземпляр класса %s \n", testClass.getName());
      ReflectionTestUtil.invokeMethod(annotationsEnumListMap.get(Annotations.BEFORE), testClassInstance);
      try {
        System.out.printf("-> -> Запускаю метод с тестом %s \n", testMethod.getName());
        ReflectionTestUtil.invokeMethod(testMethod, testClassInstance);
      } catch (TestInvokeException e) {
        testsWithExceptionCount++;
      }
      ReflectionTestUtil.invokeMethod(annotationsEnumListMap.get(Annotations.AFTER), testClassInstance);
    }
    return testsWithExceptionCount;
  }

  private void testStatistics(Map<Annotations, List<Method>> annotationsEnumListMap,
                                     int testsWithException) {
    int totalTests = annotationsEnumListMap.get(Annotations.TEST).size();
    int successfulTests = totalTests - testsWithException;
    System.out.println("СТАТИСТИКА:");
    System.out.printf("\t Всего тестов: %d, Пройдено: %d, НЕ-Пройдено: %d%n", totalTests, successfulTests, testsWithException);
  }
}