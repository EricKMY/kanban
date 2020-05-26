package domain.usecase.card.moveCard;

import domain.model.DomainEventBus;
import domain.model.workflow.Workflow;
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
//        Workflow workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
//        workflow.commitCard(input.getCardId(), input.getLaneId());
//
//        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));
//        eventBus.postAll(workflow);
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
