package domain.adapter.card;

import domain.model.card.Card;

import java.util.HashMap;
import java.util.Map;

public class CardRepository {
    Map<String, Card> map = new HashMap<String, Card>();

    public void save(Card card) {
        map.put(card.getId(), card);
    }

    public Card findById(String cardId) {
        return map.get(cardId);
    }
}