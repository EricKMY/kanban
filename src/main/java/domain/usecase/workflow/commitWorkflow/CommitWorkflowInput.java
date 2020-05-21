package domain.usecase.workflow.commitWorkflow;

public interface CommitWorkflowInput {
    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getBoardId();

    void setBoardId(String boardId);
}
