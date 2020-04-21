package domain.usecase.workflow.createWorkflow;

import domain.adapter.workflow.WorkflowRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWorkflowUseCaseTest {

    @Test
    public void createWorkflow(){
        WorkflowRepository workflowRepository = new WorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setBoardId("board00000001");
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);

//        assertEquals("board00000001", workflowRepository.findById(output.getWorkflowId()).getBoardId());
    }
}
