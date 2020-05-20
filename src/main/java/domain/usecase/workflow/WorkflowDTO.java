package domain.usecase.workflow;

import domain.usecase.lane.LaneDTO;

import java.util.ArrayList;
import java.util.List;

public class WorkflowDTO {
    private String id;
    private String name;
    private String boardId;
    private List<LaneDTO> lanes = new ArrayList<>();

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

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<LaneDTO> getLanes() {
        return lanes;
    }

    public void setLanes(List<LaneDTO> lanes) {
        this.lanes = lanes;
    }
}
