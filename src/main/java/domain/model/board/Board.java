package domain.model.board;

import java.util.UUID;

public class Board {
    private String boardName;
    private String username;
    private String boardId;

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
}
