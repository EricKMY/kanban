package domain.model.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SwimLane implements Lane{
    private String swimLaneName;
    private String swimLaneId;
    Map<String, Lane> laneList = new HashMap<String, Lane>();

    public SwimLane(String swimLaneName) {
        this.swimLaneName = swimLaneName;
        swimLaneId = "S" + UUID.randomUUID().toString();
    }

    public String getLaneId() {
        return swimLaneId;
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
}
