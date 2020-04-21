package domain.usecase.workflow.createWorkflow;

import domain.adapter.workflow.WorkflowRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import domain.usecase.CreateWorkflowInput;
import domain.usecase.CreateWorkflowOutput;

public class CreateWorkflowUseCaseTest {

    @Test
    public void createWorkflow(){
        WorkflowRepository workflowRepository = new WorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
        domain.usecase.CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setWorkflowName("Workflow1");

        createWorkflowUseCase.execute(input, output);

        assertEquals('W', output.getWorkflowId().charAt(0));
    }
}
