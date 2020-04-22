package domain.usecase.card.commitCard;

import domain.adapter.card.CardRepository;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CommitCardUseCase {
    private IWorkflowRepository workflowRepository;
    private CardRepository cardRepository;

    public CommitCardUseCase(IWorkflowRepository workflowRepository, CardRepository cardRepository) {
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
    }

    public void execute(CommitCardInput input, CommitCardOutput output) {
        Workflow workflow = workflowRepository.findById(input.getWorkflowId());
        workflow.commitCard(input.getCardId(), input.getStageId());
        workflowRepository.save(workflow);
    }
}
