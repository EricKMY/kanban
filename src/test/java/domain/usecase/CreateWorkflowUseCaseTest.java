package domain.usecase;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CreateWorkflowUseCaseTest {

    @Test
    public void createWorkflow(){
        WorkflowRepository workflowRepository = new WorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setWorkflowName("Workflow1");

        createWorkflowUseCase.execute(input, output);

        assertEquals('W', output.getWorkflowId().charAt(0));
    }
}
