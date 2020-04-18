package domain.model;

import java.util.UUID;

public class Workflow {
    private String workflowName;
    private String workflowId;

    public Workflow(String workflowName) {
        this.workflowName = workflowName;
        workflowId = "W" + UUID.randomUUID().toString();
    }

    public String getWorkflowId() {
        return workflowId;
    }
}
