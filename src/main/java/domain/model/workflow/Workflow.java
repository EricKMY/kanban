package domain.model.workflow;

import java.util.UUID;

public class Workflow {
    private String workflowName;
    private String workflowId;
    private String boardId;

    public Workflow(String workflowName, String boardId) {
        this.workflowName = workflowName;
        this.boardId = boardId;
        workflowId = "W" + UUID.randomUUID().toString();
    }

    public String getWorkflowId() {
        return workflowId;
    }
    public String getBoardId() {
        return boardId;
    }
}
