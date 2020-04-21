package domain.usecase.card.createCard;


import domain.adapter.card.CardRepository;
import domain.model.card.Card;

import java.util.HashMap;
import java.util.Map;

public class CreateCardUseCase {
    private CardRepository cardRepository;

    public CreateCardUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void execute(CreateCardInput input, CreateCardOutput output) {
        Card card = new Card(input.getCardName());
        cardRepository.save(card);

        output.setCardId(card.getCardId());
    }
}
