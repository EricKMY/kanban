package domain.usecase.domainEvent;

import domain.adapter.DomainEventInMemoryRepository;
import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.card.createCard.CreateCardPresenter;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.common.DateProvider;
import domain.model.DomainEventBus;
import domain.model.board.event.BoardCreated;
import domain.model.board.event.WorkflowCommitted;
import domain.model.card.event.CardCreated;
import domain.model.workflow.event.CardCommitted;
import domain.model.workflow.event.StageCreated;
import domain.model.workflow.event.WorkflowCreated;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.FlowEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class DomainEventTest {
    private String cardId;

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private IDomainEventRepository domainEventRepository;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String workflowId;
    private String readyLaneId;
    private String boardId;

    @Before
    public void setUp() throws Exception {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));
        eventBus.register(new FlowEventHandler(flowEventRepository));

        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));

    }

    @Test
    public void create_a_board_should_save_BoardCreated_event_in_DomainEventRepository() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        boardId = testUtility.createBoard("Eric", "kanban777");

        assertEquals(1, domainEventRepository.getAll().size());
        assertEquals(boardId, ((BoardCreated)domainEventRepository.getAll().get(0)).getBoardId());
        assertEquals("kanban777", ((BoardCreated)domainEventRepository.getAll().get(0)).getBoardName());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(0)).getOccurredOn()));
    }

    @Test
    public void create_a_workflow_should_save_WorkflowCreated_and_WorkflowCommitted_events_in_DomainEventRepository() throws ParseException {
        create_a_board_should_save_BoardCreated_event_in_DomainEventRepository();
        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));
        workflowId = testUtility.createWorkflow(boardId, "dcTrack");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(3, domainEventRepository.getAll().size());
        assertEquals(boardId, ((WorkflowCreated)domainEventRepository.getAll().get(1)).getBoardId());
        assertEquals("dcTrack", ((WorkflowCreated)domainEventRepository.getAll().get(1)).getWorkflowName());
        assertEquals(workflowId, ((WorkflowCreated)domainEventRepository.getAll().get(1)).getWorkflowId());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(1)).getOccurredOn()));

        assertEquals(boardId, ((WorkflowCommitted)domainEventRepository.getAll().get(2)).getBoardId());
        assertEquals(workflowId, ((WorkflowCommitted)domainEventRepository.getAll().get(2)).getWorkflowId());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(2)).getOccurredOn()));
    }

    @Test
    public void create_a_stage_should_save_StageCreated_event_in_DomainEventRepository() throws ParseException {
        create_a_workflow_should_save_WorkflowCreated_and_WorkflowCommitted_events_in_DomainEventRepository();
        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));
        readyLaneId = testUtility.createTopStage(workflowId, "Ready");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(4, domainEventRepository.getAll().size());
//        assertEquals(readyLaneId, ((StageCreated)domainEventRepository.getAll().get(3)).getStageId());
        assertEquals("Ready", ((StageCreated)domainEventRepository.getAll().get(3)).getStageName());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(3)).getOccurredOn()));
    }

    @Test
    public void create_a_card_should_save_CardCreated_and_CardCommitted_events_in_DomainEventRepository() throws ParseException {
        create_a_stage_should_save_StageCreated_event_in_DomainEventRepository();
        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));
        cardId = create_a_card_in_lane("implement MoveCardUseCase", readyLaneId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(6, domainEventRepository.getAll().size());
        assertEquals(workflowId, ((CardCreated)domainEventRepository.getAll().get(4)).getWorkflowId());
        assertEquals(readyLaneId, ((CardCreated)domainEventRepository.getAll().get(4)).getLaneId());
        assertEquals(cardId, ((CardCreated)domainEventRepository.getAll().get(4)).getCardId());
        assertEquals("implement MoveCardUseCase", ((CardCreated)domainEventRepository.getAll().get(4)).getCardName());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(4)).getOccurredOn()));

        assertEquals(readyLaneId, ((CardCommitted)domainEventRepository.getAll().get(5)).getLaneId());
        assertEquals(cardId, ((CardCommitted)domainEventRepository.getAll().get(5)).getCardId());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(5)).getOccurredOn()));
    }

    private String create_a_card_in_lane(String cardName, String laneId) {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);

        CreateCardInput input = (CreateCardInput) createCardUseCase;
        CreateCardOutput output = new CreateCardPresenter();

        input.setCardName(cardName);
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);

        return output.getCardId();
    }
}
