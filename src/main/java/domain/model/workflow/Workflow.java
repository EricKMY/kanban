package domain.model.workflow;

import domain.model.AggregateRoot;
import domain.model.workflow.event.WorkflowCreated;

import java.util.HashMap;
import java.util.Map;

public class Workflow extends AggregateRoot {
    private String boardId;
    Map<String, Lane> laneMap = new HashMap<String, Lane>();

    public Workflow(String workflowName, String boardId, String workflowId) {
        super(workflowName, workflowId);
        this.boardId = boardId;
    }

    public Workflow(String workflowName, String boardId) {
        super(workflowName);
        this.boardId = boardId;
        addDomainEvent(new WorkflowCreated(name, id, boardId));
    }

    public String getBoardId() {
        return boardId;
    }

    public String createStage(String stageName) {
        Lane lane = new Stage(stageName);
        laneMap.put(lane.getId(), lane);
        return lane.getId();
    }

    public String createStage(String stageName, String parentLaneId) {
        Lane lane = new Stage(stageName);
        Lane parentLane = findLaneById(parentLaneId);
        parentLane.addLane(lane);
        return lane.getId();
    }

    public String createSwimlane(String swimlaneName, String parentLaneId) {
        Lane lane = new SwimLane(swimlaneName);
        Lane parentLane = findLaneById(parentLaneId);
        parentLane.addLane(lane);
        return lane.getId();
    }

    public Lane findLaneById(String laneId) {
        if (laneMap.containsKey(laneId)){
            return laneMap.get(laneId);
        }
        Lane result;
        for (Lane lane : laneMap.values()){
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

    public void commitCard(String cardId, String laneId) {
        Lane lane = findLaneById(laneId);
        lane.addCard(cardId);
    }

    public Lane findLaneByCardId(String cardId) {
        for (Lane lane : laneMap.values()){
            if (lane.isCardContained(cardId)){
                return lane;
            }
        }
        return null;
    }

}
