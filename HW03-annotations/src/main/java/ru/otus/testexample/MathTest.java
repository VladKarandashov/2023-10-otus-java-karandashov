package ru.otus.testexample;

import ru.otus.testframework.annotation.After;
import ru.otus.testframework.annotation.Before;
import ru.otus.testframework.annotation.Test;

public class MathTest {

  private int x;
  private int y;
  private int z;

  @Before
  public void firstBeforeMethod() {
    System.out.println("Первый метод before - инициализация параметров");
    x = 1;
    y = 2;
  }

  @Before
  public void secondBeforeMethod(){
    System.out.println("Второй метод before - инициализация параметров");
    z = 0;
  }

  @After
  public void firstAfterMethod(){
    System.out.println("Первый after метод");
  }

  @After
  public void secondAfterMethod(){
    System.out.println("Второй after метод");
  }

  @Test(description = "умножение")
  public void multiplyMethod() {
    int myMultiply = 0;
    for (int i = 0; i < y; i++) {
      myMultiply += x;
    }

    int expectedMultiply = x * y;

    assert myMultiply == expectedMultiply;
  }

  @Test(description = "division by zero")
  public void methodWithException() {
    int result = y / z;
    System.out.println("Division by zero result: " + result);
  }
}