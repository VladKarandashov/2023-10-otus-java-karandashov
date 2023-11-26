package ru.otus;

import ru.otus.testexample.MathTest;
import ru.otus.testframework.TestRunner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        TestRunner.run(MathTest.class); // запуск "через класс"
        TestRunner.run("ru.otus.testexample.MathTest"); // запуск через "имя класса"
    }
}