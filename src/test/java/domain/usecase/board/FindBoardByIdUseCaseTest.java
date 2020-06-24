package domain.usecase.board;

import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.presenter.board.findById.FindBoardByIdPresenter;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.usecase.board.findBoardById.FindBoardByIdInput;
import domain.usecase.board.findBoardById.FindBoardByIdOutput;
import domain.usecase.board.findBoardById.FindBoardByIdUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.testng.Assert.assertEquals;

public class FindBoardByIdUseCaseTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private IDomainEventRepository domainEventRepository;
    private IFlowEventRepository flowEventRepository;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private String boardId;

    @Before
    public void setUp() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));

        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);
        boardId = testUtility.createBoard("user777", "kanbanSystem");
    }

    @Test
    public void find_board_by_id() {
        FindBoardByIdUseCase findBoardByIdUseCase = new FindBoardByIdUseCase(boardRepository);
        FindBoardByIdInput findBoardByIdInput = findBoardByIdUseCase;
        FindBoardByIdOutput findBoardByIdOutput = new FindBoardByIdPresenter();
        findBoardByIdInput.setBoardId(boardId);

        findBoardByIdUseCase.execute(findBoardByIdInput, findBoardByIdOutput);

        assertEquals(boardId, findBoardByIdOutput.getBoardOutputDTO().getId());
        assertEquals("kanbanSystem", findBoardByIdOutput.getBoardOutputDTO().getName());
    }
}
