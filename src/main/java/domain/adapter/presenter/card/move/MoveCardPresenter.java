package domain.adapter.presenter.card.move;

import domain.usecase.card.moveCard.MoveCardOutput;

public class MoveCardPresenter implements MoveCardOutput {
    private String cardId;

    public MoveCardViewModel build() {
        MoveCardViewModel moveCardViewModel = new MoveCardViewModel();
        moveCardViewModel.setCardId(cardId);
        return moveCardViewModel;
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
