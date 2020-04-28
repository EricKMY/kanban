package domain.model.board;

import domain.model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends Entity {

    private String username;

    List<String> workflowList = new ArrayList<String>();

    public Board(String boardName, String username, String boardId){
        super(boardName, boardId);
        this.username = username;
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
        workflowList.add(workflowId);
    }

    public boolean isWorkflowContained(String workflowId) {
        return workflowList.contains(workflowId);
    }
}
