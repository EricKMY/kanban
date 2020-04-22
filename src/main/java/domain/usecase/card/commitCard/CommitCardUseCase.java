package domain.usecase.card.commitCard;

import domain.adapter.card.CardRepository;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CommitCardUseCase {
    private IWorkflowRepository workflowRepository;

    public CommitCardUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardInput input, CommitCardOutput output) {
        Workflow workflow = workflowRepository.findById(input.getWorkflowId());
        workflow.commitCard(input.getCardId(), input.getLaneId());
        workflowRepository.save(workflow);
    }
}
