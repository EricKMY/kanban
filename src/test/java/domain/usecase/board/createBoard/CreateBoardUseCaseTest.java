package domain.usecase.board.createBoard;

import domain.adapter.board.BoardRepository;
import domain.adapter.board.BoardInMemoryRepository;
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
        IBoardRepository boardRepository = new BoardInMemoryRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername("kanban777");
        input.setBoardName("kanbanSystem");

        createBoardUseCase.execute(input, output);

        assertEquals("kanban777", boardRepository.findById(output.getBoardId()).getUsername());
    }
}
