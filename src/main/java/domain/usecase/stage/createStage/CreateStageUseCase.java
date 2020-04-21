package domain.usecase.stage.createStage;

import domain.adapter.workflow.WorkflowRepository;
import domain.model.workflow.Lane;
import domain.model.workflow.Workflow;

public class CreateStageUseCase {
    private WorkflowRepository workflowRepository;
    private Workflow workflow;

    public CreateStageUseCase(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        workflow = workflowRepository.findById(input.getWorkflowId());

        String stageId = workflow.createStage(input.getStageName());

        workflowRepository.save(workflow);

        output.setStageId(stageId);
    }
}
