package domain.model.board.event;

import domain.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class BoardCreated implements DomainEvent {
    private Date OccurredOn;

    private String userName;
    private String boardId;
    private String boardName;

    public BoardCreated(String userName, String boardId, String boardName){
        this.userName = userName;
        this.boardId = boardId;
        this.boardName = boardName;
        this.OccurredOn = DateProvider.now();
    }

    public String getUserName() {
        return userName;
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
