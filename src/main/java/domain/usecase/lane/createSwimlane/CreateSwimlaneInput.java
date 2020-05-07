package domain.usecase.lane.createSwimlane;

public class CreateSwimlaneInput {
    private String swimlaneName;
    private String workflowId;
    private String parentLaneId;

    public String getSwimlaneName() {
        return swimlaneName;
    }

    public void setSwimlaneName(String swimlaneName) {
        this.swimlaneName = swimlaneName;
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
