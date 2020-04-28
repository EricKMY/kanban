package domain.usecase.workflow.createWorkflow;

import domain.adapter.board.BoardInMemoryRepository;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateWorkflowUseCase {
    private IWorkflowRepository workflowRepository;
    private BoardInMemoryRepository boardInMemoryRepository;

    public CreateWorkflowUseCase(IWorkflowRepository workflowRepository, BoardInMemoryRepository boardInMemoryRepository) {
        this.workflowRepository = workflowRepository;
        this.boardInMemoryRepository = boardInMemoryRepository;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {
        Workflow workflow = new Workflow(input.getWorkflowName(), input.getBoardId());
        workflowRepository.save(workflow);

        output.setWorkflowId(workflow.getId());
    }
}
