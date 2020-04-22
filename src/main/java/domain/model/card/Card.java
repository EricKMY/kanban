package domain.model.card;

import java.util.UUID;

public class Card {
    private String cardId;
    private String cardName;
    private String workflowId;

    public Card(String cardName, String workflowId) {
        this.cardName = cardName;
        cardId = "C" + UUID.randomUUID().toString();
    }

    public String getCardId() {
        return cardId;
    }
}
