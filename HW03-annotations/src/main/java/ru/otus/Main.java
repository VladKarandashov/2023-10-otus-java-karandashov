package ru.otus;

import ru.otus.testexample.MathTest;
import ru.otus.testexample.WrongConstructorTest;
import ru.otus.testframework.TestRunner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        // запуск "через класс"
        TestRunner.run(MathTest.class);

        // запуск через "имя класса"
        TestRunner.run("ru.otus.testexample.MathTest");

        // запуск класса с тестами, у которого нет конструктора без аргументов, а значит не получится его просто создать
        TestRunner.run(WrongConstructorTest.class);
    }
}