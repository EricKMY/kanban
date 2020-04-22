package domain.adapter.card;


import domain.adapter.database.Database;
import domain.database.MySQL;
import domain.model.card.Card;
import domain.usecase.repository.ICardRepository;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class CardRepository implements ICardRepository {
    Map<String, Card> cards = new HashMap<String, Card>();
    private Connection connection = null;
    Database database = new MySQL();

    public CardRepository() {
        database.connect();
        database.createTable("card");
    }

    public Connection getConnection() {
        return database.connect();
    }

    public void save(Card card) {
        cards.put(card.getCardId(), card);
        convertFormat(cards.get(card.getCardId()));
        database.save(convertFormat(card));
    }

    public Card findById(String cardId) {
        Map<String, String> result = database.findById(cardId);
        Card card = getInstance(result);

        return card;
//        return cards.get(cardId);
    }

    private String[] convertFormat(Card card) {
        String attribute[] = new String[3];
        attribute[0] = card.getCardId();
        attribute[1] = card.getCardName();
        attribute[2] = card.getBlocker();

        return attribute;
    }

    private Card getInstance(Map<String, String> result) {
        String cardId = result.get("cardId");
        String cardName = result.get("cardName");
        String blocker = result.get("blocker");
        Card card = new Card(cardName);
        card.setCardId(cardId);
        card.editBlocker(blocker);

        return card;
    }
}
