package domain.adapter.repository.workflow.dto;

import domain.adapter.repository.lane.dto.LaneRepositoryDTO;

import java.util.ArrayList;
import java.util.List;

public class WorkflowRepositoryDTO {

    private String id;
    private String name;
    private String boardId;
    private List<LaneRepositoryDTO> lanes = new ArrayList<>();

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

    public List<LaneRepositoryDTO> getLanes() {
        return lanes;
    }

    public void setLanes(List<LaneRepositoryDTO> lanes) {
        this.lanes = lanes;
    }
}
