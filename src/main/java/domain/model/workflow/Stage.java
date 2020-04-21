<<<<<<< HEAD
package domain.model.workflow;

import domain.model.card.Card;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Stage {
    private String stageName;
    private String stageId;

    public Stage(String stageName) {
        this.stageName = stageName;
        stageId = "S" + UUID.randomUUID().toString();
    }

    public String getStageId() {
        return stageId;
    }
}
=======
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
>>>>>>> e8e9c1737fc80ce58f42cc8cdd31372fd6bbbb19
