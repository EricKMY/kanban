package domain.usecase.repository;

import domain.adapter.workflow.WorkflowInDatabaseRepository;
import domain.model.workflow.Workflow;
import domain.usecase.workflow.WorkflowDTOConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorkflowInDatabaseRepositoryTest {


    @Test
    public void new_a_Workflow_and_stored_into_database() {
        Workflow workflow = new Workflow("workflow2", "board00000001");
        WorkflowInDatabaseRepository workflowInDatabaseRepository = new WorkflowInDatabaseRepository();

        workflowInDatabaseRepository.save(WorkflowDTOConverter.toDTO(workflow));

        Workflow returnWorkflow = WorkflowDTOConverter.toEntity(workflowInDatabaseRepository.findById(workflow.getId()));

        assertEquals(workflow.getId(), returnWorkflow.getId());
        assertEquals(workflow.getName(), returnWorkflow.getName());
    }
}
