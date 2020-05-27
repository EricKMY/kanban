package domain.usecase.workflow;

import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.adapter.workflow.commitWorkflow.CommitWorkflowPresenter;
import domain.model.DomainEventBus;
import domain.model.board.Board;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.board.BoardRepositoryDTOConverter;
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
    private DomainEventBus eventBus;
    private String boardId;
    private TestUtility testUtility;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();
        eventBus = new DomainEventBus();

        IWorkflowRepository workflowRepository = new WorkflowInMemoryRepository();
        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        boardId = testUtility.createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void commit_a_Workflow_to_Board_aggregate() {
        String workflowId = "W012345678";
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowInput input = (CommitWorkflowInput) commitWorkflowUseCase;
        CommitWorkflowOutput output = new CommitWorkflowPresenter();

        input.setWorkflowId(workflowId);
        input.setBoardId(boardId);

        Board board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(boardId));

        assertEquals(0, board.getWorkflowList().size());
        assertFalse(board.isWorkflowContained("W012345678"));

        commitWorkflowUseCase.execute(input, output);

        board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(boardId));

        assertEquals(1, board.getWorkflowList().size());
        assertTrue(board.isWorkflowContained("W012345678"));
    }
}
