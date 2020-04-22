package domain.model.board;

import java.util.UUID;

public class Board {

    private String boardId;


    private String boardName;
    private String username;

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
}
