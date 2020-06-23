package domain.usecase.board;

import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.presenter.board.create.CreateBoardPresenter;
import domain.adapter.presenter.board.findById.FindBoardByIdPresenter;
import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.FlowEventHandler;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.board.findBoardById.FindBoardByIdInput;
import domain.usecase.board.findBoardById.FindBoardByIdOutput;
import domain.usecase.board.findBoardById.FindBoardByIdUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.testng.Assert.assertEquals;

public class FindBoardByIdUseCaseTest {
    private IBoardRepository boardRepository;
    private CreateBoardOutput createBoardOutput;
    private IWorkflowRepository workflowRepository;
    private IDomainEventRepository domainEventRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository()
;
        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardInput createBoardInput = createBoardUseCase;
        createBoardOutput = new CreateBoardPresenter();

        createBoardInput.setUserId("kanban777");
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
