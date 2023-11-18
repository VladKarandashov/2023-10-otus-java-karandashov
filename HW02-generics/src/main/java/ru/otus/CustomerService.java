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

    /**
     * Ищет entry по следующим правилам:
     * 1) если score текущего элемента равен customer.getScore(), то возвращаем следующий элемент
     * 2) если score текущего элемента больше customer.getScore(), то возвращаем текущий элемент
     * Если ничего не нашли - null
     *
     * @param customer - ключ для поиска
     * @return найденный entry
     */
    public Map.Entry<Customer, String> getNext(Customer customer) {

        var entryIterator = map.entrySet().iterator();

        while (entryIterator.hasNext()) {
            var entry = entryIterator.next();
            var scores = entry.getKey().getScores();
            if (scores == customer.getScores()) return entryIterator.next();
            if (scores > customer.getScores()) return entry;
        }
        return null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
