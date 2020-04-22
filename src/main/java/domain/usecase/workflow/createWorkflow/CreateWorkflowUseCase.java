package domain.usecase.workflow.createWorkflow;


import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateWorkflowUseCase {
    private IWorkflowRepository workflowRepository;

    public CreateWorkflowUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {
        Workflow workflow = new Workflow(input.getWorkflowName(), input.getBoardId());
        workflowRepository.save(workflow);

        output.setWorkflowId(workflow.getWorkflowId());
    }
}
