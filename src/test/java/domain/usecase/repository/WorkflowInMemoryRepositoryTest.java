package domain.usecase.repository;

import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.workflow.Workflow;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WorkflowInMemoryRepositoryTest {

    @Test
    public void getConnection() {
        WorkflowInMemoryRepository workflowInMemoryRepository = new WorkflowInMemoryRepository();
        assertNotEquals(null, workflowInMemoryRepository.getConnection());
    }

    @Test
    public void save() {
        Workflow workflow = new Workflow("workflow2", "board00000001");
        WorkflowInMemoryRepository workflowInMemoryRepository = new WorkflowInMemoryRepository();

        workflowInMemoryRepository.save(workflow);
        Workflow returnWorkflow = workflowInMemoryRepository.findById(workflow.getWorkflowId());

        assertEquals(workflow.getWorkflowId(), returnWorkflow.getWorkflowId());
        assertEquals(workflow.getWorkflowName(), returnWorkflow.getWorkflowName());
    }
}
