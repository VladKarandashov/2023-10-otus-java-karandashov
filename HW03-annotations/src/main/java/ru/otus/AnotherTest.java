package ru.otus;

import ru.otus.testframework.annotation.After;
import ru.otus.testframework.annotation.Before;
import ru.otus.testframework.annotation.Test;

public class AnotherTest {
  private int a;
  private int b;
  private int c;

  @Before
  public void firstBeforeMethod() {
    System.out.println("First before method started for second class");
    a = 1;
    b = 2;
  }

  @Before
  public void secondBeforeMethod(){
    System.out.println("Second before method started for second class");
    c = 0;
  }

  @After
  public void afterMethod(){
    System.out.println("After method started for second class");
  }

  @Test(description = "simple test for second class")
  public void sumMethod() throws NoSuchMethodException {
    throw new NoSuchMethodException();
  }

  @Test(description = "simple test  for second class")
  public void methodWithException() {
    int sum = b/c + a;
    System.out.println("Second test method result for second class: " + sum);
  }
}