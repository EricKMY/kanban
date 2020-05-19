package domain;

import domain.adapter.board.BoardInMemoryRepository;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.board.findBoardById.FindBoardByIdUseCase;
import domain.usecase.repository.IBoardRepository;

public class ApplicationContext {

    private static ApplicationContext applicationContext;
    private IBoardRepository boardInMemoryRepository;
    
    public ApplicationContext() {
        boardInMemoryRepository = new BoardInMemoryRepository();
    }

    public static ApplicationContext getInstance() {
        if(applicationContext == null) {
            applicationContext = new ApplicationContext();
        }

        return applicationContext;
    }

    public CreateBoardUseCase getCreateBoardUseCase() {
        return new CreateBoardUseCase(boardInMemoryRepository);
    }

    public FindBoardByIdUseCase getFindBoardByIdUseCase() {
        return new FindBoardByIdUseCase(boardInMemoryRepository);
    }
}
