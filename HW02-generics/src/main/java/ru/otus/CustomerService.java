package ru.otus;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        var firstEntry = map.firstEntry();
        var key = firstEntry.getKey();
        // делаем полную копию ключа, чтобы издевательства в тесте никак не влияли на ключ в коллекции
        return Map.entry(
                new Customer(key.getId(), key.getName(), key.getScores()),
                firstEntry.getValue()
        );
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        var entries = map.entrySet();
        var isNeedReturnCurrent = false;

        for (var entry : entries) {
            if (isNeedReturnCurrent) return entry;

            var key = entry.getKey();
            var scores = key.getScores();
            if (scores == customer.getScores()) isNeedReturnCurrent = true;
            if (scores > customer.getScores()) return entry;
        }
        return null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
