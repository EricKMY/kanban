package domain.usecase.repository;

import domain.adapter.database.IDatabase;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.database.MySQL;
import domain.model.workflow.Workflow;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorkflowInMemoryRepositoryTest {


    @Test
    public void save() {
        Workflow workflow = new Workflow("workflow2", "board00000001");
        IDatabase database = new MySQL();
        WorkflowInMemoryRepository workflowInMemoryRepository = new WorkflowInMemoryRepository(database);

        workflowInMemoryRepository.save(workflow);
        Workflow returnWorkflow = workflowInMemoryRepository.findById(workflow.getWorkflowId());

        assertEquals(workflow.getWorkflowId(), returnWorkflow.getWorkflowId());
        assertEquals(workflow.getWorkflowName(), returnWorkflow.getWorkflowName());
    }
}
