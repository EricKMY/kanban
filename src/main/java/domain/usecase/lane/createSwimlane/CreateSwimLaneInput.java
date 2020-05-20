package domain.usecase.lane.createSwimLane;

public class CreateSwimLaneInput {
    private String swimLaneName;
    private String workflowId;
    private String parentLaneId;

    public String getSwimLaneName() {
        return swimLaneName;
    }

    public void setSwimLaneName(String swimLaneName) {
        this.swimLaneName = swimLaneName;
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
