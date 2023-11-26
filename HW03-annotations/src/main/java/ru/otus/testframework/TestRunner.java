package ru.otus.testframework;

import ru.otus.testframework.enumerate.Annotations;
import ru.otus.testframework.exception.TestInvokeException;
import ru.otus.testframework.util.ReflectionTestUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestRunner {

  public static void run(Class<?> testClass) {
    System.out.println("______________________________________");
    System.out.printf("RUN TEST CLASS  %s", testClass.getName());
    int testsWithExceptionCount = 0;
    List<Method> classMethods = List.of(testClass.getMethods());
    Map<Annotations, List<Method>> annotatedMethodsMap = getAnnotatedMethods(classMethods);
    try {
      testsWithExceptionCount = startTests(testClass, annotatedMethodsMap);
    } catch (Exception e) {
      System.out.printf("Can't create instance for test class  %s", testClass.getName());
    }
    testStatistics(annotatedMethodsMap, testsWithExceptionCount);
  }

  private static void testStatistics(Map<Annotations, List<Method>> annotationsEnumListMap,
                                     int testsWithException) {
    int totalTests = annotationsEnumListMap.get(Annotations.TEST).size();
    int successfulTests = totalTests - testsWithException;
    System.out.println("Statistics:");
    System.out.printf("\t TOTAL TESTS: %d, PASSED: %d, FAILED: %d%n", totalTests, successfulTests, testsWithException
    );
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

  private static Map<Annotations, List<Method>> getAnnotatedMethods(List<Method> classMethods) {
    List<Annotations> annotationsList = List.of(Annotations.values());
    Map<Annotations, List<Method>> methodMap = new LinkedHashMap<>();
    annotationsList.forEach(clazz ->
            methodMap.put(clazz, ReflectionTestUtil.findAnnotatedMethods(classMethods, clazz))
    );
    return methodMap;
  }
}