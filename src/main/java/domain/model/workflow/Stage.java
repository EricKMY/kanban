package domain.model.workflow;

import java.util.*;

public class Stage implements Lane {

    private String stageName;
    private String workflowId;
    private String stageId;
    Map<String, Lane> laneList = new HashMap<String, Lane>();
    List<String> cardList = new ArrayList<String>();

    public Stage(String stageName) {
        this.stageName = stageName;
        stageId = "S" + UUID.randomUUID().toString();
    }

    public String getLaneName() {
        return stageName;
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

    public void addCard(String cardId) {
        cardList.add(cardId);
    }

    public Map<String, Lane> getChildMap(){
        return Collections.unmodifiableMap(laneList);
    }

    public boolean isCardContained(String cardId) {
        return cardList.contains(cardId);
    }

//    public String getWorkflowId() {
//
//    }
}
