package domain.adapter.board.findBoardById;

import domain.ApplicationContext;
import domain.usecase.board.findBoardById.FindBoardByIdInput;
import domain.usecase.board.findBoardById.FindBoardByIdUseCase;

public class FindBoardByIdController {
    public FindBoardByIdViewModel findBoardById(String boardId){

        FindBoardByIdUseCase findBoardByIdUseCase = ApplicationContext.getInstance().getFindBoardByIdUseCase();
        FindBoardByIdInput findBoardByIdInput = (FindBoardByIdInput)findBoardByIdUseCase;
        findBoardByIdInput.setBoardId(boardId);
        FindBoardByIdPresenter findBoardByIdPresenter = new FindBoardByIdPresenter();

        findBoardByIdUseCase.execute(findBoardByIdInput, findBoardByIdPresenter);

        FindBoardByIdViewModel findBoardByIdViewModel = findBoardByIdPresenter.build();

        return findBoardByIdViewModel;
    }
}
