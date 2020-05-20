package domain.usecase.workflow.createWorkflow;

public interface CreateWorkflowInput {

    void setWorkflowName(String workflowName);

    String getWorkflowName();

    void setBoardId(String boardId);

    String getBoardId();
}
