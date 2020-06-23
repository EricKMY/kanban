package domain.adapter.repository.lane.dto;

import domain.model.aggregate.workflow.LaneDirection;

import java.util.ArrayList;
import java.util.List;

public class LaneRepositoryDTO {
    private String id;
    private String name;
    private List<String> cardList = new ArrayList<String>();
    private LaneDirection laneDirection;
    private List<LaneRepositoryDTO> childLanes = new ArrayList<>();

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

    public List<LaneRepositoryDTO> getChildLanes() {
        return childLanes;
    }

    public void setChildLanes(List<LaneRepositoryDTO> childLanes) {
        this.childLanes = childLanes;
    }
}
