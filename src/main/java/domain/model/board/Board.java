package domain.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {

    private String boardId;


    private String boardName;
    private String username;

    List<String> workflowList = new ArrayList<String>();

    public Board(){}

    public Board(String boardName, String username) {
        this.boardName = boardName;
        this.username = username;
        boardId = "B" + UUID.randomUUID().toString();
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getBoardId() {
        return boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getUsername() {
        return username;
    }

    public void addWorkflow(String workflowId) {
        workflowList.add(workflowId);
    }

    public boolean isWorkflowContained(String workflowId) {
        return workflowList.contains(workflowId);
    }
}
