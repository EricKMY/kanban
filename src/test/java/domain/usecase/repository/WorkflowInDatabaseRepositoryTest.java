package domain.usecase.repository;

import domain.adapter.database.IDatabase;
import domain.adapter.workflow.WorkflowInDatabaseRepository;
import domain.database.MySqlDatabase;
import domain.model.workflow.Workflow;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorkflowInDatabaseRepositoryTest {


    @Test
    public void save() {
        Workflow workflow = new Workflow("workflow2", "board00000001");
        IDatabase database = new MySqlDatabase();
        WorkflowInDatabaseRepository workflowInDatabaseRepository = new WorkflowInDatabaseRepository(database);

        workflowInDatabaseRepository.save(workflow);

        Workflow returnWorkflow = workflowInDatabaseRepository.findById(workflow.getId());

        assertEquals(workflow.getId(), returnWorkflow.getId());
        assertEquals(workflow.getName(), returnWorkflow.getName());
    }
}
