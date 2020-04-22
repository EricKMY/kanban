package domain.model.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Workflow {
    private String workflowName;
    private String workflowId;
    private String boardId;
    Map<String, Lane> laneList = new HashMap<String, Lane>();

    public Workflow(String workflowName, String boardId) {
        this.workflowName = workflowName;
        this.boardId = boardId;
        workflowId = "W" + UUID.randomUUID().toString();
    }

    public String getWorkflowId() {
        return workflowId;
    }
    public String getBoardId() {
        return boardId;
    }

    public String createStage(String stageName) {
        Lane lane = new Stage(stageName);
        laneList.put(lane.getLaneId(), lane);
        return lane.getLaneId();
    }

    public String createStage(String stageName, String parentLaneId) {
        Lane lane = new Stage(stageName);
        Lane parentLane = findLaneById(parentLaneId);
        parentLane.addLane(lane);
        return lane.getLaneId();
    }

    public String createSwinlane(String swinlaneName, String parentLaneId) {
        Lane lane = new SwimLane(swinlaneName);
        Lane parentLane = findLaneById(parentLaneId);
        parentLane.addLane(lane);
        return lane.getLaneId();
    }

    public Lane findLaneById(String laneId) {
        if (laneList.containsKey(laneId)){
            return laneList.get(laneId);
        }
        Lane result;
        for (Lane lane : laneList.values()){
            if ((result = findLaneById(lane, laneId)) != null){
                return result;
            }
        }
        return null;
    }

    private Lane findLaneById(Lane lane, String laneId) {
        if (lane.getChildMap().containsKey(laneId)){
            return lane.getChildMap().get(laneId);
        }
        Lane result;
        for (Lane childLane : lane.getChildMap().values()){
            if ((result = findLaneById(lane, laneId)) != null){
                return result;
            }
        }
        return null;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void commitCard(String cardId, String laneId) {
        Lane lane = findLaneById(laneId);
        lane.addCard(cardId);
    }

    public Lane findLaneByCardId(String cardId) {
        for (Lane lane : laneList.values()){
            if (lane.isCardContained(cardId)){
                return lane;
            }
        }
        return null;
    }

}
