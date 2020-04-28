package domain.model.workflow;

import domain.model.Entity;

import java.util.*;

public abstract class Lane extends Entity {
    protected String workflowId;
    protected Map<String, Lane> laneList = new HashMap<String, Lane>();
    protected List<String> cardList = new ArrayList<String>();

    public Lane(String laneName) {
        super(laneName);
    }

    public void addLane(Lane lane) {
        laneList.put(lane.getId(), lane);
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
