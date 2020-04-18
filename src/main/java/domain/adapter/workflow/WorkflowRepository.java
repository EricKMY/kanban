package domain.adapter.workflow;

import domain.model.Workflow;

import java.util.Map;
import java.util.HashMap;

public class WorkflowRepository {
    Map<String, Workflow> map = new HashMap<String, Workflow>();

    public void add(Workflow workflow) {
        map.put(workflow.getWorkflowId(), workflow);
    }
}
