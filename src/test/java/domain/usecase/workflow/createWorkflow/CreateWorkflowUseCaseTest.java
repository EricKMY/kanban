package domain.usecase.workflow.createWorkflow;

import domain.adapter.workflow.WorkflowInMemoryRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWorkflowUseCaseTest {

    @Test
    public void createWorkflow(){
        WorkflowInMemoryRepository workflowInMemoryRepository = new WorkflowInMemoryRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowInMemoryRepository);
        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setBoardId("board00000001");
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);

        assertEquals("board00000001", workflowInMemoryRepository.findById(output.getWorkflowId()).getBoardId());
    }
}
