package io.servlet.examples.example_02_request_response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GamesPricesStorage {

    private final Map<String, Integer> gameToPrice;

    public GamesPricesStorage() {
        this.gameToPrice = new ConcurrentHashMap<>();
    }

    Integer create(String gameName, int price) {
        return gameToPrice.putIfAbsent(gameName, price);
    }

    Integer read(String gameName) {
        return gameToPrice.get(gameName);
    }

    Integer update(String gameName, int newPrice) {
        return gameToPrice.replace(gameName, newPrice);
    }

    void delete(String gameName) {
        gameToPrice.remove(gameName);
    }
}
