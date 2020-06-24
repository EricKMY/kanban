package domain.usecase.workflow;

import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.adapter.presenter.workflow.commit.CommitWorkflowPresenter;
import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.adapter.repository.board.converter.BoardRepositoryDTOConverter;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowInput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowOutput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommitWorkflowUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private IFlowEventRepository flowEventRepository;
    private IDomainEventRepository domainEventRepository;
    private DomainEventBus eventBus;
    private String boardId;
    private TestUtility testUtility;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));

        workflowRepository = new WorkflowInMemoryRepository();
        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        boardId = testUtility.createBoard("user777", "kanbanSystem");
    }

    @Test
    public void commit_a_Workflow_to_Board_aggregate() {
        String workflowId = "W012345678";

        Board board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(boardId));

        assertEquals(0, board.getWorkflowList().size());

        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowInput input = commitWorkflowUseCase;
        CommitWorkflowOutput output = new CommitWorkflowPresenter();

        input.setWorkflowId(workflowId);
        input.setBoardId(boardId);

        commitWorkflowUseCase.execute(input, output);

        assertNotNull(output.getWorkflowId());

        board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(boardId));

        assertEquals(1, board.getWorkflowList().size());
        assertTrue(board.isWorkflowContained(workflowId));
    }
}
