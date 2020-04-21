package domain.usecase.stage.createStage;

import domain.adapter.stage.StageRepository;
import domain.model.workflow.Stage;

public class CreateStageUseCase {
    private StageRepository stageRepository;

    public CreateStageUseCase(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {
        Stage stage = new Stage(input.getStageName());
        stageRepository.add(stage);

        output.setStageId(stage.getStageId());
    }
}
