package domain.usecase.stage.createStage;

import domain.model.workflow.Workflow;

public class CreateStageUseCase {
    private Workflow workflow;

    public CreateStageUseCase(Workflow workflow) {
        this.workflow = workflow;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {
        String stageId = workflow.createStage(input.getStageName());

        output.setStageId(stageId);
    }
}
