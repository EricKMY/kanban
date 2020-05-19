package domain.usecase.board.createBoard;

public interface CreateBoardInput {

    public String getBoardName();

    public void setBoardName(String boardName);

    public String getUsername();

    public void setUsername(String username);
}
