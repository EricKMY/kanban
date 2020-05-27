package domain.usecase.card;

import domain.adapter.DomainEventInMemoryRepository;
import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.card.calculateCycleTime.CalculateCycleTimePresenter;
import domain.adapter.card.createCard.CreateCardPresenter;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.common.DateProvider;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.FlowEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeInput;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeOutput;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeUseCase;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.card.cycleTime.CycleTime;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.lane.LaneDTOConverter;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class CalculateCycleTimeInLaneTest {
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
    private String analysisLaneId;
    private String cardName;
    private String developmentLaneId;
    private String testLaneId;
    private String deployLaneId;


    @Before
    public void setup() throws Exception {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new FlowEventHandler(flowEventRepository));

        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        readyLaneId = testUtility.createTopStage(workflowId, "Ready");
        analysisLaneId = testUtility.createTopStage(workflowId, "Analysis");
        developmentLaneId = testUtility.createTopStage(workflowId, "Development");
        testLaneId = testUtility.createTopStage(workflowId, "Test");
        deployLaneId = testUtility.createTopStage(workflowId, "Deploy");
        cardName = "implement MoveCardUseCase";
        cardId = create_a_card_in_lane(cardName, readyLaneId);
    }

    @Test
    public void calculate_cycleTime_should_count_lead_time_without_moving_card() throws ParseException {

        DateProvider.setDate(dateFormat.parse("2020/5/27 20:54:00"));

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository);
        CalculateCycleTimeInput input = calculateCycleTimeUseCase;
        input.setWorkflowId(workflowId);
        input.setCardId(cardId);
        input.setBeginningLaneId(readyLaneId);
        input.setEndingLaneId(readyLaneId);

        CalculateCycleTimeOutput output = new CalculateCycleTimePresenter();

        calculateCycleTimeUseCase.execute(input, output);

        assertEquals(1, output.getCycleTime().getDiffDays());
        assertEquals(1, output.getCycleTime().getDiffHours());
        assertEquals(0, output.getCycleTime().getDiffMinutes());
        assertEquals(0, output.getCycleTime().getDiffSeconds());
    }

    @Test
    public void calculate_cycleTime_should_count_lead_time_with_moving_card() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/27 20:54:00"));
        testUtility.moveCard(workflowId, cardId, readyLaneId, analysisLaneId);
        CycleTime cycleTime = testUtility.calculateCycleTime(workflowId, cardId, readyLaneId, analysisLaneId);

        assertEquals(1, cycleTime.getDiffDays());
        assertEquals(1, cycleTime.getDiffHours());
        assertEquals(0, cycleTime.getDiffMinutes());
        assertEquals(0, cycleTime.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/29 23:57:10"));
        testUtility.moveCard(workflowId, cardId, analysisLaneId, developmentLaneId);
        cycleTime = testUtility.calculateCycleTime(workflowId, cardId, readyLaneId, developmentLaneId);

        assertEquals(3, cycleTime.getDiffDays());
        assertEquals(4, cycleTime.getDiffHours());
        assertEquals(3, cycleTime.getDiffMinutes());
        assertEquals(10, cycleTime.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/30 23:57:10"));
        testUtility.moveCard(workflowId, cardId, developmentLaneId, testLaneId);
        cycleTime = testUtility.calculateCycleTime(workflowId, cardId, readyLaneId, testLaneId);

        assertEquals(4, cycleTime.getDiffDays());
        assertEquals(4, cycleTime.getDiffHours());
        assertEquals(3, cycleTime.getDiffMinutes());
        assertEquals(10, cycleTime.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/31 23:57:10"));
        testUtility.moveCard(workflowId, cardId, testLaneId, deployLaneId);
        cycleTime = testUtility.calculateCycleTime(workflowId, cardId, readyLaneId, deployLaneId);

        assertEquals(5, cycleTime.getDiffDays());
        assertEquals(4, cycleTime.getDiffHours());
        assertEquals(3, cycleTime.getDiffMinutes());
        assertEquals(10, cycleTime.getDiffSeconds());
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
