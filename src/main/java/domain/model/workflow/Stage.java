package domain.model.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Stage implements Lane {
    private String stageName;
    private String workflowId;
    private String stageId;
    Map<String, Lane> laneList = new HashMap<String, Lane>();

    public Stage(String stageName) {
        this.stageName = stageName;
        stageId = "S" + UUID.randomUUID().toString();
    }

    public String getLaneId() {
        return stageId;
    }

    public void addLane(Lane lane) {
        laneList.put(lane.getLaneId(), lane);
    }

    public int getChildAmount() {
        return laneList.size();
    }

    public Lane findById(String laneId) {
        return laneList.get(laneId);
    }

//    public String getWorkflowId() {
//
//    }
}
