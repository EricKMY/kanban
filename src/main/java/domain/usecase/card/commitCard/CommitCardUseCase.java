package domain.usecase.card.commitCard;

import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class CommitCardUseCase implements CommitCardInput {
    private IWorkflowRepository workflowRepository;
    private String cardId;
    private String workflowId;
    private String laneId;
    private DomainEventBus eventBus;

    public CommitCardUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CommitCardInput input, CommitCardOutput output) {
        Workflow workflow = WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        workflow.commitCard(input.getCardId(), input.getLaneId());

        workflowRepository.save(WorkflowRepositoryDTOConverter.toDTO(workflow));
        eventBus.postAll(workflow);

        output.setCardId(input.getCardId());
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
