package domain.model.workflow;

import java.util.Map;

public interface Lane {
    String getLaneId();
    void addLane(Lane lane);
    int getChildAmount();
//    public String getWorkflowId();
    Lane findById(String laneId);

    void addCard(String cardId);
    Map<String, Lane> getChildMap();
    boolean isCardContained(String cardId);

    String getLaneName();
}
