package domain.model.board;

import domain.model.AggregateRoot;
import domain.model.board.event.BoardCreated;
import domain.model.board.event.WorkflowCommitted;
import domain.model.workflow.event.WorkflowCreated;

import java.util.ArrayList;
import java.util.List;

public class Board extends AggregateRoot {

    private String userName;

    List<String> workflowList = new ArrayList<String>();

    public Board(String boardName, String userName, String boardId, List<String> workflowList){
        super(boardName, boardId);
        this.userName = userName;
        this.workflowList.addAll(workflowList);
    }

    public Board(String boardName, String userName) {
        super(boardName);
        this.userName = userName;
        addDomainEvent(new BoardCreated(userName, id, boardName));
    }

    public String getUserName() {
        return userName;
    }

    public void addWorkflow(String workflowId) {
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
