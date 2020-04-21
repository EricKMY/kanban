package domain.model.board;

import java.util.UUID;

public class Board {
    private String boardName;
    private String boardId;

    public Board(String boardName) {
        this.boardName = boardName;
        boardId = "B" + UUID.randomUUID().toString();
    }

    public String getBoardId() {
        return boardId;
    }
}
