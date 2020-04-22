package domain.usecase.workflow.createWorkflow;

import domain.adapter.board.BoardRepository;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateWorkflowUseCase {
    private IWorkflowRepository workflowRepository;
    private BoardRepository boardRepository;

    public CreateWorkflowUseCase(IWorkflowRepository workflowRepository, BoardRepository boardRepository) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {
        Workflow workflow = new Workflow(input.getWorkflowName(), input.getBoardId());
        workflowRepository.save(workflow);

        output.setWorkflowId(workflow.getWorkflowId());
    }
}
