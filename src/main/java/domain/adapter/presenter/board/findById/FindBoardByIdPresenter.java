package domain.adapter.presenter.board.findById;

import domain.usecase.board.BoardOutputDTO;
import domain.usecase.board.findBoardById.FindBoardByIdOutput;

public class FindBoardByIdPresenter implements FindBoardByIdOutput {

    private BoardOutputDTO boardOutputDTO;

    @Override
    public BoardOutputDTO getBoardOutputDTO() {
        return boardOutputDTO;
    }

    @Override
    public void setBoardOutputDTO(BoardOutputDTO boardOutputDTO) {
        this.boardOutputDTO = boardOutputDTO;
    }

    public FindBoardByIdViewModel build(){
        FindBoardByIdViewModel findBoardByIdViewModel = new FindBoardByIdViewModel();
        findBoardByIdViewModel.setName(boardOutputDTO.getName());
        return findBoardByIdViewModel;
    }
}
