package domain.usecase.card.moveCard;

import domain.model.DomainEventBus;
import domain.model.card.Card;
import domain.model.workflow.Workflow;
import domain.usecase.card.CardDTOConverter;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class MoveCardUseCase implements MoveCardInput {
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;
    private String workflowId;
    private String laneId;
    private String targetLaneId;
    private String cardId;

    public MoveCardUseCase(IWorkflowRepository workflowRepository, ICardRepository cardRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;
    }

    public void execute(MoveCardInput input, MoveCardOutput output) {
        Card card = CardDTOConverter.toEntity(cardRepository.findById(input.getCardId()));

        card.moveCard(input.getWorkflowId(), input.getTargetLaneId());

        cardRepository.save(CardDTOConverter.toDTO(card));
        eventBus.postAll(card);
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
    public String getLaneId() {
        return laneId;
    }

    @Override
    public void setLaneId(String laneId) {
        this.laneId = laneId;
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
