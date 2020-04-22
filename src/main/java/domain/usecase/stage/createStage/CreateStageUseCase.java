package domain.usecase.stage.createStage;

import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateStageUseCase {
    private IWorkflowRepository workflowRepository;
    private Workflow workflow;

    public CreateStageUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        workflow = workflowRepository.findById(input.getWorkflowId());

        String stageId = workflow.createStage(input.getStageName());

        workflowRepository.save(workflow);

        output.setStageId(stageId);
    }
}
