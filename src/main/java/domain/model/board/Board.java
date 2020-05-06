package domain.model.board;

import domain.model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Board extends Entity {

    private String username;

    List<String> workflows = new ArrayList<String>();

    public Board(String boardName, String username, String boardId, List<String> workflows){
        super(boardName, boardId);
        this.username = username;
        this.workflows.addAll(workflows);
    }

    public Board(String boardName, String username) {
        super(boardName);
        this.username = username;
    }

//    public void setBoardName(String boardName) {
//        this.boardName = boardName;
//    }
//
//    public String getBoardName() {
//        return boardName;
//    }

    public String getUsername() {
        return username;
    }

    public void addWorkflow(String workflowId) {
        workflows.add(workflowId);
    }

    public boolean isWorkflowContained(String workflowId) {
        return workflows.contains(workflowId);
    }

    public List<String> getWorkflows() {
        return workflows;
    }
}
