package domain.usecase.card.createCard;

import domain.model.DomainEventBus;
import domain.model.card.Card;
import domain.model.card.event.CardCreated;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;

public class CreateCardUseCase {
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;

    public CreateCardUseCase(ICardRepository cardRepository, DomainEventBus eventBus) {
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateCardInput input, CreateCardOutput output) {
        Card card = new Card(input.getCardName(), input.getLaneId(), input.getWorkflowId());
        cardRepository.save(card);

        output.setCardId(card.getId());
        eventBus.postAll(card);
        
    }
}
