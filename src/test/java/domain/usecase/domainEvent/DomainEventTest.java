package domain.usecase.domainEvent;

import domain.adapter.presenter.card.cycleTime.CalculateCycleTimePresenter;
import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.model.aggregate.workflow.event.CardUncommitted;
import domain.model.common.DateProvider;
import domain.model.DomainEventBus;
import domain.model.aggregate.board.event.BoardCreated;
import domain.model.aggregate.board.event.WorkflowCommitted;
import domain.model.aggregate.card.event.CardCreated;
import domain.model.aggregate.workflow.event.CardCommitted;
import domain.model.aggregate.workflow.event.StageCreated;
import domain.model.aggregate.workflow.event.WorkflowCreated;
import domain.model.service.event.CycleTimeCalculated;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.FlowEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeInput;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeOutput;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class DomainEventTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private IDomainEventRepository domainEventRepository;
    private IFlowEventRepository flowEventRepository;

    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private String workflowId;
    private String readyLaneId;
    private String boardId;
    private String cardId;
    private SimpleDateFormat dateFormat;

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

        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));

    }

    @Test
    public void create_a_board_should_generate_BoardCreated_event() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        boardId = testUtility.createBoard("Eric", "board777");

        assertEquals(1, domainEventRepository.getAll().size());
        assertEquals(boardId, ((BoardCreated)domainEventRepository.getAll().get(0)).getBoardId());
        assertEquals("board777", ((BoardCreated)domainEventRepository.getAll().get(0)).getBoardName());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(0)).getOccurredOn()));
    }

    @Test
    public void create_a_workflow_should_generate_WorkflowCreated_and_WorkflowCommitted_events() throws ParseException {
        create_a_board_should_generate_BoardCreated_event();
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
    public void create_a_stage_should_generate_StageCreated_event() throws ParseException {
        create_a_workflow_should_generate_WorkflowCreated_and_WorkflowCommitted_events();
        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));
        readyLaneId = testUtility.createTopStage(workflowId, "Ready");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(4, domainEventRepository.getAll().size());
        assertEquals(readyLaneId, ((StageCreated)domainEventRepository.getAll().get(3)).getStageId());
        assertEquals(workflowId, ((StageCreated)domainEventRepository.getAll().get(3)).getWorkflowId());
        assertEquals("Ready", ((StageCreated)domainEventRepository.getAll().get(3)).getStageName());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(3)).getOccurredOn()));
    }

    @Test
    public void create_a_card_should_generate_CardCreated_and_CardCommitted_events() throws ParseException {
        create_a_stage_should_generate_StageCreated_event();
        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));
        cardId = testUtility.createCard("implement MoveCardUseCase", workflowId, readyLaneId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(6, domainEventRepository.getAll().size());
        assertEquals(workflowId, ((CardCreated)domainEventRepository.getAll().get(4)).getWorkflowId());
        assertEquals(readyLaneId, ((CardCreated)domainEventRepository.getAll().get(4)).getLaneId());
        assertEquals(cardId, ((CardCreated)domainEventRepository.getAll().get(4)).getCardId());
        assertEquals("implement MoveCardUseCase", ((CardCreated)domainEventRepository.getAll().get(4)).getCardName());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(4)).getOccurredOn()));

        assertEquals(readyLaneId, ((CardCommitted)domainEventRepository.getAll().get(5)).getLaneId());
        assertEquals(cardId, ((CardCommitted)domainEventRepository.getAll().get(5)).getCardId());
        assertEquals(workflowId, ((CardCommitted)domainEventRepository.getAll().get(5)).getWorkflowId());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(5)).getOccurredOn()));
    }

    @Test
    public void move_a_card_should_generate_CardUncommitted_and_CardCommitted_events() throws ParseException {
        boardId = testUtility.createBoard("Eric", "board777");
        workflowId = testUtility.createWorkflow(boardId, "dcTrack");
        String backlogLaneId = testUtility.createTopStage(workflowId, "backlog");
        String planningLaneId = testUtility.createTopStage(workflowId, "planning");
        DateProvider.setDate(dateFormat.parse("2020/6/23 19:54:00"));
        cardId = testUtility.createCard("implement MoveCardUseCase", workflowId, backlogLaneId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(7, domainEventRepository.getAll().size());

        testUtility.moveCard(workflowId, cardId, backlogLaneId, planningLaneId);

        assertEquals(10, domainEventRepository.getAll().size());
        assertEquals(3, flowEventRepository.getAll().size());

        assertEquals(backlogLaneId, ((CardUncommitted)domainEventRepository.getAll().get(8)).getLaneId());
        assertEquals(cardId, ((CardUncommitted)domainEventRepository.getAll().get(8)).getCardId());
        assertEquals("2020/06/23", sdf.format((domainEventRepository.getAll().get(8)).getOccurredOn()));

        assertEquals(planningLaneId, ((CardCommitted)domainEventRepository.getAll().get(9)).getLaneId());
        assertEquals(cardId, ((CardCommitted)domainEventRepository.getAll().get(9)).getCardId());
        assertEquals("2020/06/23", sdf.format((domainEventRepository.getAll().get(9)).getOccurredOn()));

    }

    @Test
    public void calculate_cycleTime_should_generate_CycleTimeCalculated_event() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/6/23 19:54:00"));
        boardId = testUtility.createBoard("Eric", "board777");
        workflowId = testUtility.createWorkflow(boardId, "dcTrack");
        String backlogLaneId = testUtility.createTopStage(workflowId, "backlog");
        String planningLaneId = testUtility.createTopStage(workflowId, "planning");
        cardId = testUtility.createCard("implement MoveCardUseCase", workflowId, backlogLaneId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        DateProvider.setDate(dateFormat.parse("2020/6/26 19:54:00"));

        assertEquals(7, domainEventRepository.getAll().size());

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, eventBus);
        CalculateCycleTimeInput input = calculateCycleTimeUseCase;
        input.setWorkflowId(workflowId);
        input.setCardId(cardId);
        input.setBeginningStageId(backlogLaneId);
        input.setEndingStageId(planningLaneId);

        CalculateCycleTimeOutput output = new CalculateCycleTimePresenter();

        calculateCycleTimeUseCase.execute(input, output);

        assertEquals(8, domainEventRepository.getAll().size());

        assertEquals("2020/06/26", sdf.format(domainEventRepository.getAll().get(7).getOccurredOn()));
        assertEquals(cardId, ((CycleTimeCalculated)domainEventRepository.getAll().get(7)).getCardId());
        assertEquals(259200,((CycleTimeCalculated) domainEventRepository.getAll().get(7)).getCycleTime());
    }
}
