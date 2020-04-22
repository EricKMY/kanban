package domain.model.workflow;

import java.util.*;

public class SwimLane implements Lane{
    private String swimLaneName;
    private String swimLaneId;
    Map<String, Lane> laneList = new HashMap<String, Lane>();
    List<String> cardList = new ArrayList<String>();

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

    public Map<String, Lane> getChildMap(){
        return Collections.unmodifiableMap(laneList);
    }

    public void addCard(String cardId) {
        cardList.add(cardId);
    }

    public boolean isCardContained(String cardId) {
        return cardList.contains(cardId);
    }

    public String getLaneName() {
        return swimLaneName;
    }
}
