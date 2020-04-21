package domain.usecase.workflow.createWorkflow;

public class CreateWorkflowInput {
    private String workflowName;
    private String boardId;

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }
}
