package domain.usecase.board;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.board.createBoard.CreateBoardPresenter;
import domain.adapter.board.findBoardById.FindBoardByIdPresenter;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.board.findBoardById.FindBoardByIdInput;
import domain.usecase.board.findBoardById.FindBoardByIdOutput;
import domain.usecase.board.findBoardById.FindBoardByIdUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.testng.Assert.assertEquals;

public class FindBoardByIdUseCaseTest {
    private IBoardRepository boardRepository;
    private CreateBoardOutput createBoardOutput;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardInput createBoardInput = (CreateBoardInput) createBoardUseCase;
        createBoardOutput = new CreateBoardPresenter();

        createBoardInput.setUsername("kanban777");
        createBoardInput.setBoardName("kanbanSystem");

        createBoardUseCase.execute(createBoardInput, createBoardOutput);
    }

    @Test
    public void find_board_by_id() {
        FindBoardByIdUseCase findBoardByIdUseCase = new FindBoardByIdUseCase(boardRepository, eventBus);
        FindBoardByIdInput findBoardByIdInput = (FindBoardByIdInput) findBoardByIdUseCase;
        FindBoardByIdOutput findBoardByIdOutput = new FindBoardByIdPresenter();
        findBoardByIdInput.setBoardId(createBoardOutput.getBoardId());

        findBoardByIdUseCase.execute(findBoardByIdInput, findBoardByIdOutput);

        assertEquals("kanbanSystem", findBoardByIdOutput.getBoardOutputDTO().getName());

    }
}
