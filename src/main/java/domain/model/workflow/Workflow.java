package domain.model.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Workflow {
    private String workflowName;
    private String workflowId;
    private String boardId;
    Map<String, Lane> stageList = new HashMap<String, Lane>();

    public Workflow(String workflowName, String boardId) {
        this.workflowName = workflowName;
        this.boardId = boardId;
        workflowId = "W" + UUID.randomUUID().toString();
    }

    public String getWorkflowId() {
        return workflowId;
    }
    public String getBoardId() {
        return boardId;
    }

    public String createStage(String stageName) {
        Lane stage = new Stage(stageName);
        stageList.put(stage.getLaneId(), stage);
        return stage.getLaneId();
    }

    public Lane findLaneById(String laneId) {
        return stageList.get(laneId);
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
