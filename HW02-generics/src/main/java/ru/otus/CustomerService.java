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
        if (firstEntry == null) {
            return null;
        }
        // делаем полную копию ключа, чтобы издевательства в тесте никак не влияли на ключ в коллекции
        return Map.entry(new Customer(firstEntry.getKey()), firstEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var findEntry = map.higherEntry(customer);
        if (findEntry == null) {
            return null;
        }
        // делаем полную копию ключа, чтобы издевательства в тесте никак не влияли на ключ в коллекции
        return Map.entry(new Customer(findEntry.getKey()), findEntry.getValue());
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
