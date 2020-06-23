package domain.usecase.board.createBoard;

public interface CreateBoardInput {

    String getBoardName();

    void setBoardName(String boardName);

    String getUserId();

    void setUserId(String username);
}
