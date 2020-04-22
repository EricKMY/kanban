package domain.adapter.workflow;

import domain.database.MySQL;
import domain.adapter.database.Database;
import domain.usecase.repository.IWorkflowRepository;
import domain.model.workflow.Workflow;

import java.sql.Connection;

import java.util.Map;
import java.util.HashMap;

public class WorkflowInMemoryRepository implements IWorkflowRepository {
    Map<String, Workflow> map = new HashMap<String, Workflow>();
    private Connection connection = null;
    Database database = new MySQL();

    public WorkflowInMemoryRepository() {
        database.createTable("workflow");
    }

    public Connection getConnection() {
        return database.connect();
    }

    public void save(Workflow workflow) {
        map.put(workflow.getWorkflowId(), workflow);
        convertFormat(map.get(workflow.getWorkflowId()));
        database.save(convertFormat(workflow));
    }

    public Workflow findById(String workflowId) {
        Map<String, String> result = database.findById(workflowId);
        Workflow workflow = getInstance(result);

        return workflow;
    }

    private String[] convertFormat(Workflow workflow) {
        String attribute[] = new String[3];
        attribute[0] = workflow.getWorkflowId();
        attribute[1] = workflow.getWorkflowName();
        attribute[2] = workflow.getBoardId();

        return attribute;
    }

    private Workflow getInstance(Map<String, String> result) {
        String workflowId = result.get("workflowId");
        String workflowName = result.get("workflowName");
        String boardId = result.get("boardId");
        Workflow workflow = new Workflow(workflowName, boardId);
        workflow.setWorkflowId(workflowId);

        return workflow;
    }
}
