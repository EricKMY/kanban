package domain.usecase.repository;

import domain.adapter.workflow.WorkflowRepository;
import domain.model.workflow.Workflow;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WorkflowRepositoryTest {

    @Test
    public void getConnection() {
        WorkflowRepository workflowRepository = new WorkflowRepository();
        assertNotEquals(null, workflowRepository.getConnection());
    }

    @Test
    public void save() {
        Workflow workflow = new Workflow("workflow2", "board00000001");
        WorkflowRepository workflowRepository = new WorkflowRepository();

        workflowRepository.save(workflow);
        Workflow returnWorkflow = workflowRepository.findById(workflow.getWorkflowId());

        assertEquals(workflow.getWorkflowId(), returnWorkflow.getWorkflowId());
        assertEquals(workflow.getWorkflowName(), returnWorkflow.getWorkflowName());
    }
}
