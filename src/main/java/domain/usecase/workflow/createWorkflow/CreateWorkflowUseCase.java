package domain.usecase;

import domain.adapter.workflow.WorkflowRepository;
import domain.model.Workflow;

public class CreateWorkflowUseCase {
    private WorkflowRepository workflowRepository;

    public CreateWorkflowUseCase(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {
        Workflow workflow = new Workflow(input.getWorkflowName());
        workflowRepository.add(workflow);

        output.setWorkflowId(workflow.getWorkflowId());
    }
}
