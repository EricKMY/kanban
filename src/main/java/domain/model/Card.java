package domain.model;

import java.util.UUID;

public class Card {
    private String cardId;
    private String cardName;

    public Card(String cardName) {
        this.cardName = cardName;
        cardId = "C" + UUID.randomUUID().toString();
    }

    public String getCardId() {
        return cardId;
    }
}
