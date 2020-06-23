package domain.adapter.controller.board.create;

import domain.adapter.presenter.board.create.CreateBoardPresenter;
import domain.adapter.presenter.board.create.CreateBoardViewModel;
import domain.model.DomainEventBus;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IBoardRepository;

public class CreateBoardController {
    public CreateBoardViewModel createBoard(String userId, String boardName, IBoardRepository boardRepository, DomainEventBus eventBus){

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardInput createBoardInput = (CreateBoardInput)createBoardUseCase;

        createBoardInput.setUserId(userId);
        createBoardInput.setBoardName(boardName);

        CreateBoardPresenter createBoardPresenter = new CreateBoardPresenter();

        createBoardUseCase.execute(createBoardInput, createBoardPresenter);

        CreateBoardViewModel createBoardViewModel = createBoardPresenter.build();

        return createBoardViewModel;
    }
}
