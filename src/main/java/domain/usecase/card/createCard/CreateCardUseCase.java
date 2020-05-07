package domain.usecase.card.createCard;

import domain.model.DomainEventBus;
import domain.model.card.Card;
import domain.usecase.card.CardDTOConverter;
import domain.usecase.repository.ICardRepository;

public class CreateCardUseCase {
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;

    public CreateCardUseCase(ICardRepository cardRepository, DomainEventBus eventBus) {
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateCardInput input, CreateCardOutput output) {
        Card card = new Card(input.getCardName(), input.getLaneId(), input.getWorkflowId());
        cardRepository.save(CardDTOConverter.toDTO(card));

        output.setCardId(card.getId());
        eventBus.postAll(card);
        
    }
}
