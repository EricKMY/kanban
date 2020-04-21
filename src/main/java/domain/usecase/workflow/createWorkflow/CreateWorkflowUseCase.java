package domain.usecase.workflow.createWorkflow;


import domain.adapter.workflow.WorkflowRepository;
import domain.model.workflow.Workflow;

public class CreateWorkflowUseCase {
    private WorkflowRepository workflowRepository;

    public CreateWorkflowUseCase(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {
        Workflow workflow = new Workflow(input.getWorkflowName(), input.getBoardId());
        workflowRepository.add(workflow);

        output.setWorkflowId(workflow.getWorkflowId());
    }
}
