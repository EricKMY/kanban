package domain.adapter.workflow;

import domain.adapter.database.IDatabase;
import domain.usecase.repository.IWorkflowRepository;
import domain.model.workflow.Workflow;

import java.sql.Connection;

import java.util.Map;

public class WorkflowInDatabaseRepository implements IWorkflowRepository {

    IDatabase database;

    public WorkflowInDatabaseRepository(IDatabase database) {
        this.database = database;
        database.createTable("workflow");
    }

    public Connection getConnection() {
        return database.connect();
    }

    public void save(Workflow workflow) {
        convertFormat(workflow);
        database.save(convertFormat(workflow));
    }

    public Workflow findById(String workflowId) {
        Map<String, String> result = database.findById(workflowId);
        Workflow workflow = getInstance(result);

        return workflow;
    }

    private String[] convertFormat(Workflow workflow) {
        String attribute[] = new String[3];
        attribute[0] = workflow.getId();
        attribute[1] = workflow.getName();
        attribute[2] = workflow.getBoardId();

        return attribute;
    }

    private Workflow getInstance(Map<String, String> result) {
        String workflowId = result.get("workflowId");
        String workflowName = result.get("workflowName");
        String boardId = result.get("boardId");
        Workflow workflow = new Workflow(workflowName, workflowId);

        return workflow;
    }
}
