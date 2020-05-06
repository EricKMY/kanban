package domain.usecase.board;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.board.BoardInDatabaseRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IBoardRepository;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateBoardUseCaseTest {
    @Test
    public void createBoard(){
        BoardInMemoryRepository boardInMemoryRepository = new BoardInMemoryRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardInMemoryRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername("kanban777");
        input.setBoardName("kanbanSystem");

        createBoardUseCase.execute(input, output);

        assertEquals("kanban777", boardInMemoryRepository.findById(output.getBoardId()).getUsername());
    }

    @Test
    public void createBoardInDB(){
        IBoardRepository boardRepository = new BoardInDatabaseRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername("kanban777");
        input.setBoardName("kanbanSystem");

        createBoardUseCase.execute(input, output);

        assertEquals("kanban777", boardRepository
                                            .findById(output.getBoardId())
                                            .getUsername());
    }
}
