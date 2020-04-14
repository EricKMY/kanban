package domain.usecase;

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
