package domain.usecase.card.uncommitCard;

import domain.model.DomainEventBus;
import domain.model.workflow.Workflow;
import domain.usecase.card.uncommitCard.UncommitCardInput;
import domain.usecase.card.uncommitCard.UncommitCardOutput;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class UncommitCardUseCase implements UncommitCardInput {
    private IWorkflowRepository workflowRepository;
    private String cardId;
    private String workflowId;
    private String laneId;
    private DomainEventBus eventBus;

    public UncommitCardUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(UncommitCardInput input, UncommitCardOutput output) {
        Workflow workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        workflow.uncommitCard(input.getCardId(), input.getLaneId());

        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));
        eventBus.postAll(workflow);
    }

    public String getCardId() {
        return cardId;
    }

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

    public String getLaneId() {
        return laneId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }
}
