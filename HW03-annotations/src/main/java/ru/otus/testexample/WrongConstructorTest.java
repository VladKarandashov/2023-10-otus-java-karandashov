package ru.otus.testexample;

import ru.otus.testframework.annotation.After;
import ru.otus.testframework.annotation.Before;
import ru.otus.testframework.annotation.Test;

public class WrongConstructorTest {

    public WrongConstructorTest(int unusedVariable) {
    }

    @Before
    public void beforeMethod() {
        System.out.println("метод before - для класса с отсутствующим конструктором без аргументов");
    }

    @After
    public void afterMethod(){
        System.out.println("метод after - для класса с отсутствующим конструктором без аргументов");
    }


    @Test
    public void testMethod() {
        System.out.println("тестовый метод - для класса с отсутствующим конструктором без аргументов");
    }
}
