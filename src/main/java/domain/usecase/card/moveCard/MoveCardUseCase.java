package domain.usecase.card.moveCard;

import domain.adapter.repository.card.converter.CardRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.card.Card;
import domain.usecase.card.CardDTOConverter;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;

public class MoveCardUseCase implements MoveCardInput {
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;
    private String workflowId;
    private String originLaneId;
    private String targetLaneId;
    private String cardId;

    public MoveCardUseCase(IWorkflowRepository workflowRepository, ICardRepository cardRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;
    }

    public void execute(MoveCardInput input, MoveCardOutput output) {
        Card card = CardRepositoryDTOConverter.toEntity(cardRepository.findById(input.getCardId()));

        card.moveCard(input.getWorkflowId(), input.getTargetLaneId());

        cardRepository.save(CardRepositoryDTOConverter.toDTO(card));
        eventBus.postAll(card);

        output.setCardId(card.getId());
    }

    @Override
    public String getCardId() {
        return cardId;
    }

    @Override
    public void setCardId(String cardId) {
        this.cardId = cardId;
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
    public String getOriginLaneId() {
        return originLaneId;
    }

    @Override
    public void setOriginLaneId(String laneId) {
        this.originLaneId = laneId;
    }

    @Override
    public String getTargetLaneId() {
        return targetLaneId;
    }

    @Override
    public void setTargetLaneId(String targetLaneId) {
        this.targetLaneId = targetLaneId;
    }
}
