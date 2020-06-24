package domain.usecase.workflow;

import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.adapter.presenter.workflow.create.CreateWorkflowPresenter;
import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.adapter.repository.board.converter.BoardRepositoryDTOConverter;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
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
    private ICardRepository cardRepository;
    private IFlowEventRepository flowEventRepository;
    private IDomainEventRepository domainEventRepository;
    private String boardId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();


        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));

        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        boardId = testUtility.createBoard("user777", "kanbanSystem");
    }

    @Test
    public void create_a_Workflow_should_succeed(){
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);
        CreateWorkflowInput input = createWorkflowUseCase;
        CreateWorkflowOutput output = new CreateWorkflowPresenter();

        input.setBoardId(boardId);
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);

        Workflow workflow = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(output.getWorkflowId()));

        assertNotNull(output.getWorkflowId());

        assertEquals(boardId, workflow.getBoardId());
        assertEquals("defaultWorkflow", workflow.getName());
        assertEquals(output.getWorkflowId(), workflow.getId());
        assertEquals(0, workflow.getLaneMap().size());
    }

    @Test
    public void create_a_Workflow_should_commit_to_its_Board(){
        Board board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(boardId));

        assertEquals(0, board.getWorkflowList().size());

        String workflowId = testUtility.createWorkflow(board.getId(), "defaultWorkflow");

        board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(boardId));

        assertEquals(1, board.getWorkflowList().size());
        assertTrue(board.isWorkflowContained(workflowId));

    }
}
