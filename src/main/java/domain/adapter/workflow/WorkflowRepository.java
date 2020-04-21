package domain.adapter.workflow;

import domain.adapter.Database;
import domain.adapter.MySQL;
import domain.usecase.Repository;
import domain.model.workflow.Workflow;

import java.sql.Connection;
import java.util.Map;
import java.util.HashMap;

public class WorkflowRepository implements Repository {
    Map<String, Workflow> map = new HashMap<String, Workflow>();
    private Connection connection = null;
    Database database = new MySQL();

    public WorkflowRepository() {
//        database.connect();
//        database.createTable("workflow");
    }

    public Connection getConnection() {
        return database.connect();
    }

    public void add(Workflow workflow) {
        map.put(workflow.getWorkflowId(), workflow);
//        convertFormat(map.get(workflow.getWorkflowId()));
//        database.save(convertFormat(workflow));
    }

    public Workflow findById(String workflowId) {
        return map.get(workflowId);
    }

    private String[] convertFormat(Workflow workflow) {
        String attribute[] = new String[2];
        attribute[0] = workflow.getWorkflowId();
        attribute[1] = workflow.getWorkflowName();

        return attribute;
    }
}
