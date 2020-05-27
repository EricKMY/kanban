package domain.usecase.workflow;

import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.adapter.workflow.createWorkflow.CreateWorkflowPresenter;
import domain.model.DomainEventBus;
import domain.model.board.Board;
import domain.model.workflow.Workflow;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.board.BoardRepositoryDTO;
import domain.usecase.board.BoardRepositoryDTOConverter;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateWorkflowUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String boardId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private CreateWorkflowInput input;
    private CreateWorkflowOutput output;
    private CreateWorkflowUseCase createWorkflowUseCase;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();


        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        boardId = testUtility.createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void create_a_Workflow(){
        createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);
        input = (CreateWorkflowInput) createWorkflowUseCase;
        output = new CreateWorkflowPresenter();

        input.setBoardId(boardId);
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);

        Workflow workflow = WorkflowDTOConverter.toEntity(
                workflowRepository.findById(output.getWorkflowId())
        );

        assertEquals(boardId, workflow.getBoardId());
        assertEquals("defaultWorkflow", workflow.getName());
    }

    @Test
    public void create_a_Workflow_should_commit_to_its_Board(){
        Board board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(boardId));

        assertEquals(0, board.getWorkflowList().size());

        create_a_Workflow();

        board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(boardId));

        assertEquals(1, board.getWorkflowList().size());
        assertTrue(board.isWorkflowContained(output.getWorkflowId()));

    }
}
