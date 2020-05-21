package domain.adapter.board.createBoard;

import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IBoardRepository;

public class CreateBoardController {
    public CreateBoardViewModel createBoard(String userName, String boardName, IBoardRepository boardRepository){

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput createBoardInput = (CreateBoardInput)createBoardUseCase;

        createBoardInput.setUsername(userName);
        createBoardInput.setBoardName(boardName);

        CreateBoardPresenter createBoardPresenter = new CreateBoardPresenter();

        createBoardUseCase.execute(createBoardInput, createBoardPresenter);

        CreateBoardViewModel createBoardViewModel = createBoardPresenter.build();

        return createBoardViewModel;
    }
}
