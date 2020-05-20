package domain.adapter.board.createBoard;

import domain.ApplicationContext;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardUseCase;

public class CreateBoardController {
    public CreateBoardViewModel createBoard(String userName, String boardName){

        CreateBoardUseCase createBoardUseCase = ApplicationContext.getInstance().getCreateBoardUseCase();
        CreateBoardInput createBoardInput = (CreateBoardInput)createBoardUseCase;

        createBoardInput.setUsername(userName);
        createBoardInput.setBoardName(boardName);

        CreateBoardPresenter createBoardPresenter = new CreateBoardPresenter();

        createBoardUseCase.execute(createBoardInput, createBoardPresenter);

        CreateBoardViewModel createBoardViewModel = createBoardPresenter.build();

        return createBoardViewModel;
    }
}
