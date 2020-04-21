package domain.model.workflow;

import java.util.UUID;

public interface Lane {
    public String getLaneId();
    public void addLane(Lane lane);
    public int getChildAmount();
//    public String getWorkflowId();
    public Lane findById(String laneId);
}
