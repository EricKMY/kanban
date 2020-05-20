package domain.usecase.card.createCard;

import domain.model.DomainEventBus;
import domain.model.card.Card;
import domain.usecase.card.CardDTOConverter;
import domain.usecase.repository.ICardRepository;

public class CreateCardUseCase implements CreateCardInput {
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;
    private String cardName;
    private String workflowId;
    private String laneId;

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

    @Override
    public String getCardName() {
        return cardName;
    }

    @Override
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String getLaneId() {
        return laneId;
    }

    @Override
    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }
}
