package ru.otus;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import java.util.List;

@SuppressWarnings("java:S106")
public class App {
    public static void main(String... args) {
        String input = "example input string by karandashov vlad";

        // Разбиваем входную строку на слова с помощью Guava Splitter
        List<String> words = Splitter.on(' ').splitToList(input);
        // Удаляем повторяющиеся символы с помощью Guava ImmutableSet
        ImmutableSet<String> uniqueWords = ImmutableSet.copyOf(words);
        // Сортируем список слов по убыванию длины с помощью Guava Ordering
        List<String> sortedWords = Ordering.natural().reverse().sortedCopy(uniqueWords);
        // Соединяем отсортированные слова в строку с помощью Guava Joiner
        String result = Joiner.on(',').join(sortedWords);

        System.out.println(result);
    }
}
