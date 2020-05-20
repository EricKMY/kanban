package domain.usecase.card.commitCard;

import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class CommitCardUseCase {
    private IWorkflowRepository workflowRepository;

    public CommitCardUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardInput input, CommitCardOutput output) {
        Workflow workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        workflow.commitCard(input.getCardId(), input.getLaneId());
        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));
    }
}
