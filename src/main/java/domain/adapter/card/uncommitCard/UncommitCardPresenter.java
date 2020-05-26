package domain.adapter.card.uncommitCard;

import domain.usecase.card.uncommitCard.UncommitCardOutput;

public class UncommitCardPresenter implements UncommitCardOutput {
    private String cardId;

    public UncommitCardViewModel build() {
        UncommitCardViewModel uncommitCardViewModel = new UncommitCardViewModel();
        uncommitCardViewModel.setCardId(cardId);
        return uncommitCardViewModel;
    }

    @Override
    public String getCardId() {
        return cardId;
    }

    @Override
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
