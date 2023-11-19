package ru.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    // oldScore -> customer+data
    private final TreeMap<Long, Map.Entry<Customer, String>> map = new TreeMap<>();

    // id -> oldScore
    private final Map<Long, Long> oldScoreById = new HashMap<>();


    public Map.Entry<Customer, String> getSmallest() {
        var firstEntry = map.firstEntry();
        var customerWithData = firstEntry.getValue();
        var customer = customerWithData.getKey();
        // делаем полную копию ключа, чтобы издевательства в тесте никак не влияли на ключ в коллекции
        return Map.entry(new Customer(customer), customerWithData.getValue());
    }

    /**
     * Судя по поведению в тесте - сортировка должна идти по изначальным score,
     * для этого они были сохранены отдельно.
     * Поэтому мы сначала по id пытаемся найти старый score
     * (если его нет, то берём из входного параметра),
     * и затем мы уже ищем, основываясь на старом score.
     *
     * @param customer - ключ для поиска
     * @return найденный entry
     */
    public Map.Entry<Customer, String> getNext(Customer customer) {

        var oldScore = oldScoreById.get(customer.getId());
        var score = oldScore == null ? customer.getScores() : oldScore;

        var find = map.higherEntry(score);

        return find == null ? null : find.getValue();
    }

    public void add(Customer customer, String data) {
        map.put(customer.getScores(), Map.entry(customer,data));
        oldScoreById.put(customer.getId(), customer.getScores());
    }
}
