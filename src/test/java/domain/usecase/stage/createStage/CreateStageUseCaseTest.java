package domain.usecase.stage.createStage;

import domain.adapter.stage.StageRepository;
import domain.usecase.stage.createStage.CreateStageInput;
import domain.usecase.stage.createStage.CreateStageOutput;
import domain.usecase.stage.createStage.CreateStageUseCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateStageUseCaseTest {

    @Test
    public void CreateStage() {
        StageRepository stageRepository = new StageRepository();
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(stageRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Stage1");

        createStageUseCase.execute(input, output);

        assertEquals('S', output.getStageId().charAt(0));
    }
}
