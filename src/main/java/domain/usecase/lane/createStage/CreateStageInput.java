package domain.usecase.lane.createStage;

public class CreateStageInput {
    private String stageName;
    private String workflowId;
    private String parentLaneId;

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getParentLaneId() {
        return parentLaneId;
    }

    public void setParentLaneId(String parentLaneId) {
        this.parentLaneId = parentLaneId;
    }
}
