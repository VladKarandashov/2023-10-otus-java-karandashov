package ru.otus.testframework.enumerate;


import java.lang.annotation.Annotation;
import ru.otus.testframework.annotation.After;
import ru.otus.testframework.annotation.Before;
import ru.otus.testframework.annotation.Test;

/**
 * Вспомогательный enum для разбора аннотаций
 */
public enum Annotations {
  BEFORE(Before.class),
  TEST(Test.class),
  AFTER(After.class);

  private final Class<? extends Annotation> annotationClass;

  Annotations(Class<? extends Annotation> annotationClass) {
    this.annotationClass = annotationClass;
  }

  public Class<? extends Annotation> getAnnotationClass() {
    return annotationClass;
  }
}