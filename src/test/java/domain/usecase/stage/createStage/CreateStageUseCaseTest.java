package domain.usecase.stage.createStage;

import domain.adapter.workflow.WorkflowRepository;
import domain.model.workflow.Lane;
import domain.model.workflow.Stage;
import domain.model.workflow.SwimLane;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateStageUseCaseTest {
    private WorkflowRepository workflowRepository;
    private String workflowId;
    private String laneId;

    @Before
    public void setup() {
        workflowRepository = new WorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setBoardId("board00000001");
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);
        workflowId = output.getWorkflowId();
        laneId = workflowRepository.findById(workflowId).createStage("TopStage");
    }

    @Test
    public void createTopStage() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(
                workflowRepository.findById(workflowId));
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Backlog");

        createStageUseCase.execute(input, output);

        assertEquals('S', output.getStageId().charAt(0));
    }

    @Test
    public void createLane() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(
                workflowRepository.findById(workflowId));
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Backlog");

        createStageUseCase.execute(input, output);

        Lane swimLane = new SwimLane("BottomLane");

        workflowRepository
                .findById(workflowId)
                .findById(laneId)
                .addLane(swimLane);

        assertEquals(1,
                workflowRepository.findById(workflowId)
                .findById(laneId)
                .getChildAmount());

    }
}
