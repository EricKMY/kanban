package domain.usecase.lane;

import domain.model.workflow.LaneDirection;

import java.util.ArrayList;
import java.util.List;

public class LaneDTO {
    private String id;
    private String name;
    private List<String> cardList = new ArrayList<String>();
    private LaneDirection laneDirection;
    private List<LaneDTO> childLanes = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCardList() {
        return cardList;
    }

    public void setCardList(List<String> cardList) {
        this.cardList = cardList;
    }

    public LaneDirection getLaneDirection() {
        return laneDirection;
    }

    public void setLaneDirection(LaneDirection laneDirection) {
        this.laneDirection = laneDirection;
    }

    public List<LaneDTO> getChildLanes() {
        return childLanes;
    }

    public void setChildLanes(List<LaneDTO> childLanes) {
        this.childLanes = childLanes;
    }
}
