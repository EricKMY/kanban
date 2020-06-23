package domain.model.aggregate.board.event;

import domain.model.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class BoardCreated implements DomainEvent {
    private Date OccurredOn;

    private String userId;
    private String boardId;
    private String boardName;

    public BoardCreated(String userId, String boardId, String boardName){
        this.userId = userId;
        this.boardId = boardId;
        this.boardName = boardName;
        this.OccurredOn = DateProvider.now();
    }

    public String getUserId() {
        return userId;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getDetail() {
        return "Board Created: " + boardName;
    }

    public Date getOccurredOn() {
        return OccurredOn;
    }
}
