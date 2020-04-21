package domain.usecase.stage.createStage;

import domain.adapter.workflow.WorkflowRepository;
import domain.model.workflow.Workflow;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateStageUseCaseTest {
    private WorkflowRepository workflowRepository;
    private String workflowId;

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
    }

    @Test
    public void CreateStage() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(
                workflowRepository.findById(workflowId));
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Backlog");

        createStageUseCase.execute(input, output);

        assertEquals('S', output.getStageId().charAt(0));
    }
}
