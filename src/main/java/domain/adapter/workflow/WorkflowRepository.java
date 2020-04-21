package domain.adapter.workflow;

import domain.model.workflow.Workflow;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;

import java.util.Map;
import java.util.HashMap;

public class WorkflowRepository {
    Map<String, Workflow> map = new HashMap<String, Workflow>();

    public void save(Workflow workflow) {
        map.put(workflow.getWorkflowId(), workflow);
    }


    public Workflow findById(String workflowId) {
        return map.get(workflowId);
    }
}
