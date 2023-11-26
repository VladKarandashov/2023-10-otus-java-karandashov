package ru.otus;

import ru.otus.testframework.TestRunner;

public class Main {
    public static void main(String[] args) {
        TestRunner.run(MathTest.class);
        TestRunner.run(AnotherTest.class);
    }
}