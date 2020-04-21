package domain.usecase.workflow.createWorkflow;

public class CreateWorkflowOutput {
    private String workflowId;
    private String boardId;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getBoardId() {
        return boardId;
    }
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
