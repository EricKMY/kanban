package domain.model.aggregate.board;

import domain.model.aggregate.AggregateRoot;
import domain.model.aggregate.board.event.BoardCreated;
import domain.model.aggregate.board.event.WorkflowCommitted;

import java.util.ArrayList;
import java.util.List;

public class Board extends AggregateRoot {

    private String userId;

    List<String> workflowList = new ArrayList<String>();

    public Board(String boardName, String userId, String boardId, List<String> workflowList){
        super(boardName, boardId);
        this.userId = userId;
        this.workflowList.addAll(workflowList);
    }

    public Board(String boardName, String userId) {
        super(boardName);
        this.userId = userId;
        addDomainEvent(new BoardCreated(userId, id, boardName));
    }

    public String getUserId() {
        return userId;
    }

    public void commitWorkflow(String workflowId) {
        workflowList.add(workflowId);
        addDomainEvent(new WorkflowCommitted(id, workflowId));
    }

    public boolean isWorkflowContained(String workflowId) {
        return workflowList.contains(workflowId);
    }

    public List<String> getWorkflowList() {
        return workflowList;
    }
}
