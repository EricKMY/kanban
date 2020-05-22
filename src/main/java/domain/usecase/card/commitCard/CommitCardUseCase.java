package domain.usecase.card.commitCard;

import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowInput;

public class CommitCardUseCase implements CommitCardInput {
    private IWorkflowRepository workflowRepository;
    private String cardId;
    private String workflowId;
    private String laneId;

    public CommitCardUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardInput input, CommitCardOutput output) {
        Workflow workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        workflow.commitCard(input.getCardId(), input.getLaneId());
        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));
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
