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
  public static void run(String className) throws ClassNotFoundException {
    Class<?> testClass = Class.forName(className);
    run(testClass);
  }

  /**
   * Запуск тестового класса
   *
   * @param testClass - тестовый класс
   */
  public static void run(Class<?> testClass) {
    System.out.println("______________________________________");
    System.out.printf("RUN TEST CLASS  %s", testClass.getName());
    int testsWithExceptionCount = 0;
    List<Method> classMethods = List.of(testClass.getMethods());
    Map<Annotations, List<Method>> annotatedMethodsMap = ReflectionTestUtil.getAnnotatedMethods(classMethods);
    try {
      testsWithExceptionCount = startTests(testClass, annotatedMethodsMap);
    } catch (Exception e) {
      System.out.printf("Can't create instance for test class  %s", testClass.getName());
    }
    testStatistics(annotatedMethodsMap, testsWithExceptionCount);
  }

  private static int startTests(Class<?> testClass, Map<Annotations, List<Method>> annotationsEnumListMap)
          throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    int testsWithExceptionCount = 0;
    for (Method testMethod : annotationsEnumListMap.get(Annotations.TEST)) {
      var newInstance = testClass.getConstructor().newInstance();
      ReflectionTestUtil.invokeMethod(annotationsEnumListMap.get(Annotations.BEFORE), newInstance);
      try {
        ReflectionTestUtil.invokeMethod(testMethod, newInstance);
      } catch (TestInvokeException e) {
        testsWithExceptionCount++;
      }
      ReflectionTestUtil.invokeMethod(annotationsEnumListMap.get(Annotations.AFTER), newInstance);
    }
    return testsWithExceptionCount;
  }

  private static void testStatistics(Map<Annotations, List<Method>> annotationsEnumListMap,
                                     int testsWithException) {
    int totalTests = annotationsEnumListMap.get(Annotations.TEST).size();
    int successfulTests = totalTests - testsWithException;
    System.out.println("Statistics:");
    System.out.printf("\t TOTAL TESTS: %d, PASSED: %d, FAILED: %d%n", totalTests, successfulTests, testsWithException);
  }
}