package ru.otus.testframework.exception;

/**
 * Исключение тестового фреймворка
 */
public class TestInvokeException extends RuntimeException {

  public TestInvokeException(String message) {
    super(message);
  }
}