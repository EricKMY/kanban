package domain.usecase.board.createBoard;

import domain.adapter.board.BoardRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateBoardUseCaseTest {
    @Test
    public void createBoard(){
        BoardRepository boardRepository = new BoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setBoardName("Board1");

        createBoardUseCase.execute(input, output);

        assertEquals('B', output.getBoardId().charAt(0));
    }
}
