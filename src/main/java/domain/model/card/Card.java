package domain.model.card;

import java.util.UUID;

public class Card {
    private String cardId;
    private String cardName;
    private String workflowId;
    private String blocker = "";

    public Card(String cardName, String workflowId) {
        this.cardName = cardName;
        cardId = "C" + UUID.randomUUID().toString();
    }

    public String getCardId() {
        return cardId;
    }

    public void editBlocker(String blocker) {
        this.blocker = blocker;
    }

    public String getBlocker() {
        return blocker;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
