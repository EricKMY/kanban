package domain.usecase.card.editBlocker;

public class EditBlockerInput {
    private String cardId;
    private String blocker;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBlocker() {
        return blocker;
    }

    public void setBlocker(String blocker) {
        this.blocker = blocker;
    }
}
