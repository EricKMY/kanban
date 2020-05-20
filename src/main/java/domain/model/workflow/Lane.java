package domain.model.workflow;

import domain.model.Entity;

import java.util.*;

public abstract class Lane extends Entity {
    protected Map<String, Lane> laneMap = new HashMap<String, Lane>();
    protected List<String> cardList = new ArrayList<String>();
    protected LaneDirection laneDirection;

    public Lane(String laneName, LaneDirection laneDirection) {
        super(laneName);
        this.laneDirection = laneDirection;
    }

    public Lane(String laneName, LaneDirection laneDirection, String laneId, List<String> cardList) {
        super(laneName, laneId);
        this.laneDirection = laneDirection;
        this.cardList = cardList;
    }

    public void addLane(Lane lane) {
        laneMap.put(lane.getId(), lane);
    }

    public int getChildAmount() {
        return laneMap.size();
    }

    public Lane findById(String laneId) {
        return laneMap.get(laneId);
    }

    public void addCard(String cardId) {
        cardList.add(cardId);
    }

    public Map<String, Lane> getChildMap(){
        return Collections.unmodifiableMap(laneMap);
    }

    public boolean isCardContained(String cardId) {
        return cardList.contains(cardId);
    }

    public List<String> getCardList() {
        return cardList;
    }

    public LaneDirection getLaneDirection() {
        return laneDirection;
    }
}
