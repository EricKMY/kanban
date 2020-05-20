package domain.adapter.board.createBoard;

import domain.usecase.board.createBoard.CreateBoardOutput;

public class CreateBoardPresenter implements CreateBoardOutput {
    private String boardId;

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public CreateBoardViewModel build(){
        CreateBoardViewModel createBoardViewModel = new CreateBoardViewModel();
        createBoardViewModel.setBoardId(boardId);
        return createBoardViewModel;
    }
}
