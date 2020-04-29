package domain.usecase.workflow.createWorkflow;

import domain.adapter.board.BoardInMemoryRepository;
import domain.model.DomainEventBus;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateWorkflowUseCase {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateWorkflowUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {
        Workflow workflow = new Workflow(input.getWorkflowName(), input.getBoardId());
        workflowRepository.save(workflow);
        eventBus.postAll(workflow);

        output.setWorkflowId(workflow.getId());
    }
}
