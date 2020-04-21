package domain.model.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Workflow {
    private String workflowName;
    private String workflowId;
    private String boardId;
    Map<String, Stage> stageList = new HashMap<String, Stage>();

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
        Stage stage = new Stage(stageName);
        stageList.put(stage.getStageId(), stage);
        return stage.getStageId();
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
