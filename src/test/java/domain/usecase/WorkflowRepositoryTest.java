package domain.usecase;

import domain.adapter.workflow.WorkflowRepository;
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
//        Workflow workflow = new Workflow("workflow2");
//        Workflow w;
//        WorkflowRepository workflowRepository = new WorkflowRepository();
//        workflowRepository.add(workflow);
//        w = workflowRepository.findById(workflow.getWorkflowId());
//        assertEquals(workflow.getWorkflowId(), w.getWorkflowId());
//        assertEquals(workflow.getWorkflowName(), w.getWorkflowName());
    }
}
