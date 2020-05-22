package domain.adapter.card.commitCard;

import domain.adapter.card.createCard.CreateCardViewModel;
import domain.usecase.card.commitCard.CommitCardOutput;

public class CommitCardPresenter implements CommitCardOutput {
    private String cardId;

    public CommitCardViewModel build() {
        CommitCardViewModel commitCardViewModel = new CommitCardViewModel();
        commitCardViewModel.setCardId(cardId);
        return commitCardViewModel;
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
