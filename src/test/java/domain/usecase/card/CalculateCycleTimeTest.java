package domain.usecase.card;

import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.presenter.card.cycleTime.CalculateCycleTimePresenter;
import domain.adapter.presenter.card.create.CreateCardPresenter;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.model.common.DateProvider;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.FlowEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeInput;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeOutput;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeUseCase;
import domain.usecase.card.calculateCycleTime.CycleTimeModel;
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

public class CalculateCycleTimeTest {
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
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));
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

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, eventBus);
        CalculateCycleTimeInput input = calculateCycleTimeUseCase;
        input.setWorkflowId(workflowId);
        input.setCardId(cardId);
        input.setBeginningLaneId(readyLaneId);
        input.setEndingLaneId(readyLaneId);

        CalculateCycleTimeOutput output = new CalculateCycleTimePresenter();

        calculateCycleTimeUseCase.execute(input, output);

        assertEquals(1, output.getCycleTimeModel().getDiffDays());
        assertEquals(1, output.getCycleTimeModel().getDiffHours());
        assertEquals(0, output.getCycleTimeModel().getDiffMinutes());
        assertEquals(0, output.getCycleTimeModel().getDiffSeconds());
    }

    @Test
    public void calculate_cycleTime_should_count_lead_time_with_moving_card() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/27 20:54:00"));
        testUtility.moveCard(workflowId, cardId, readyLaneId, analysisLaneId);
        CycleTimeModel cycleTimeModel = testUtility.calculateCycleTime(workflowId, cardId, readyLaneId, analysisLaneId);

        assertEquals(1, cycleTimeModel.getDiffDays());
        assertEquals(1, cycleTimeModel.getDiffHours());
        assertEquals(0, cycleTimeModel.getDiffMinutes());
        assertEquals(0, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/29 23:57:10"));
        testUtility.moveCard(workflowId, cardId, analysisLaneId, developmentLaneId);
        cycleTimeModel = testUtility.calculateCycleTime(workflowId, cardId, readyLaneId, developmentLaneId);

        assertEquals(3, cycleTimeModel.getDiffDays());
        assertEquals(4, cycleTimeModel.getDiffHours());
        assertEquals(3, cycleTimeModel.getDiffMinutes());
        assertEquals(10, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/30 23:57:10"));
        testUtility.moveCard(workflowId, cardId, developmentLaneId, testLaneId);
        cycleTimeModel = testUtility.calculateCycleTime(workflowId, cardId, readyLaneId, testLaneId);

        assertEquals(4, cycleTimeModel.getDiffDays());
        assertEquals(4, cycleTimeModel.getDiffHours());
        assertEquals(3, cycleTimeModel.getDiffMinutes());
        assertEquals(10, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/31 23:57:10"));
        testUtility.moveCard(workflowId, cardId, testLaneId, deployLaneId);
        cycleTimeModel = testUtility.calculateCycleTime(workflowId, cardId, readyLaneId, deployLaneId);

        assertEquals(5, cycleTimeModel.getDiffDays());
        assertEquals(4, cycleTimeModel.getDiffHours());
        assertEquals(3, cycleTimeModel.getDiffMinutes());
        assertEquals(10, cycleTimeModel.getDiffSeconds());
    }

    private String create_a_card_in_lane(String cardName, String laneId) {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);

        CreateCardInput input = createCardUseCase;
        CreateCardOutput output = new CreateCardPresenter();

        input.setCardName(cardName);
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);

        return output.getCardId();
    }

}
