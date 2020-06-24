package domain.adapter.controller.board.findById;

import domain.adapter.presenter.board.findById.FindBoardByIdPresenter;
import domain.adapter.presenter.board.findById.FindBoardByIdViewModel;
import domain.usecase.board.findBoardById.FindBoardByIdInput;
import domain.usecase.board.findBoardById.FindBoardByIdUseCase;
import domain.usecase.repository.IBoardRepository;

public class FindBoardByIdController {
    public FindBoardByIdViewModel findBoardById(String boardId, IBoardRepository boardRepository){
        FindBoardByIdUseCase findBoardByIdUseCase = new FindBoardByIdUseCase(boardRepository);
        FindBoardByIdInput findBoardByIdInput = findBoardByIdUseCase;
        findBoardByIdInput.setBoardId(boardId);
        FindBoardByIdPresenter findBoardByIdPresenter = new FindBoardByIdPresenter();

        findBoardByIdUseCase.execute(findBoardByIdInput, findBoardByIdPresenter);

        FindBoardByIdViewModel findBoardByIdViewModel = findBoardByIdPresenter.build();

        return findBoardByIdViewModel;
    }
}
