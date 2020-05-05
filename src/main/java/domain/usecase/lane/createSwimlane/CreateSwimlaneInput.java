package domain.usecase.lane.createSwimlane;

public class CreateSwimlaneInput {
    private String swinlaneName;
    private String workflowId;
    private String parentLaneId;

    public String getSwinlaneName() {
        return swinlaneName;
    }

    public void setSwinlaneName(String swinlaneName) {
        this.swinlaneName = swinlaneName;
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
