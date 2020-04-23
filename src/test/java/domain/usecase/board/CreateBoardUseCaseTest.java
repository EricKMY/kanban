package domain.usecase.board;

import domain.adapter.board.BoardRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.database.IDatabase;
import domain.database.MySQL;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IBoardRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateBoardUseCaseTest {
    @Test
    public void createBoard(){
        BoardRepository boardRepository = new BoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername("kanban777");
        input.setBoardName("kanbanSystem");

        createBoardUseCase.execute(input, output);

        assertEquals("kanban777", boardRepository.findById(output.getBoardId()).getUsername());
    }

    @Test
    public void createBoardInDB(){
        IDatabase database = new MySQL();
        IBoardRepository boardRepository = new BoardInMemoryRepository(database);
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername("kanban777");
        input.setBoardName("kanbanSystem");

        createBoardUseCase.execute(input, output);

        assertEquals("kanban777", boardRepository.findById(output.getBoardId()).getUsername());
    }
}
