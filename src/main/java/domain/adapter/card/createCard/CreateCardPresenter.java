package domain.adapter.card.createCard;

import domain.usecase.card.createCard.CreateCardOutput;

public class CreateCardPresenter implements CreateCardOutput {
    private String cardId;

    public CreateCardViewModel build() {
        CreateCardViewModel createCardViewModel = new CreateCardViewModel();
        createCardViewModel.setCardId(cardId);
        return createCardViewModel;
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
