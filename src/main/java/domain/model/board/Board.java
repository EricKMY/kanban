package domain.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {
    private String boardName;
    private String username;
    private String boardId;
    List<String> workflowList = new ArrayList<String>();

    public Board(String boardName, String username) {
        this.boardName = boardName;
        this.username = username;
        boardId = "B" + UUID.randomUUID().toString();
    }

    public String getBoardId() {
        return boardId;
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
