package domain.usecase.lane.createStage;

public interface CreateStageInput {
    String getStageName();

    void setStageName(String stageName);

    void setWorkflowId(String workflowId);

    String getWorkflowId();

    String getParentLaneId();

    void setParentLaneId(String parentLaneId);
}
