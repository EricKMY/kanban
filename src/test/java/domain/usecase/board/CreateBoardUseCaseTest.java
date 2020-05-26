package domain.usecase.board;

import domain.adapter.board.BoardInDatabaseRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.board.createBoard.CreateBoardPresenter;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateBoardUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();

        eventBus = new DomainEventBus();
    }

    @Test
    public void create_a_Board_and_stored_into_memory(){
        BoardInMemoryRepository boardInMemoryRepository = new BoardInMemoryRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardInMemoryRepository, eventBus);
        CreateBoardInput input = createBoardUseCase;
        CreateBoardOutput output = new CreateBoardPresenter();

        input.setUsername("kanban777");
        input.setBoardName("kanbanSystem");

        createBoardUseCase.execute(input, output);

        assertEquals("kanban777", boardInMemoryRepository.findById(output.getBoardId()).getUsername());
    }

    @Test
    public void create_a_Board_and_stored_into_database(){
        IBoardRepository boardRepository = new BoardInDatabaseRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardInput input = (CreateBoardInput) createBoardUseCase;
        CreateBoardOutput output = new CreateBoardPresenter();

        input.setUsername("kanban777");
        input.setBoardName("kanbanSystem");

        createBoardUseCase.execute(input, output);

        assertEquals("kanban777", boardRepository
                                            .findById(output.getBoardId())
                                            .getUsername());
    }
}
