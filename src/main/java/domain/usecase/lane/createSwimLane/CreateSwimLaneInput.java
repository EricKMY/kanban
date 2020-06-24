package domain.usecase.lane.createSwimLane;

public interface CreateSwimLaneInput {

    String getSwimLaneName();

    void setSwimLaneName(String swimLaneName);

    void setWorkflowId(String workflowId);

    String getWorkflowId();

    String getParentLaneId();

    void setParentLaneId(String parentLaneId);
}
