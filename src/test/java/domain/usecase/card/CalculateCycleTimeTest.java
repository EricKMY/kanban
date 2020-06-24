package domain.usecase.card;

import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.presenter.card.cycleTime.CalculateCycleTimePresenter;
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
    private String readyStageId;
    private String analysisStageId;
    private String developmentStageId;
    private String testStageId;
    private String deployStageId;
    private String cardName;


    @Before
    public void setup() throws Exception {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));
        eventBus.register(new FlowEventHandler(flowEventRepository));

        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        DateProvider.setDate(dateFormat.parse("2020/5/26 19:54:00"));

        String boardId = testUtility.createBoard("user777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        readyStageId = testUtility.createTopStage(workflowId, "Ready");
        analysisStageId = testUtility.createTopStage(workflowId, "Analysis");
        developmentStageId = testUtility.createTopStage(workflowId, "Development");
        testStageId = testUtility.createTopStage(workflowId, "Test");
        deployStageId = testUtility.createTopStage(workflowId, "Deploy");
        cardName = "implement MoveCardUseCase";
        cardId = testUtility.createCard(cardName, workflowId, readyStageId);
    }

    @Test
    public void calculate_cycleTime_without_moving_card_should_count_detention_time() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/27 20:54:00"));

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, eventBus);
        CalculateCycleTimeInput input = calculateCycleTimeUseCase;
        input.setWorkflowId(workflowId);
        input.setCardId(cardId);
        input.setBeginningStageId(readyStageId);
        input.setEndingStageId(readyStageId);

        CalculateCycleTimeOutput output = new CalculateCycleTimePresenter();

        calculateCycleTimeUseCase.execute(input, output);

        assertEquals(1, output.getCycleTimeModel().getDiffDays());
        assertEquals(1, output.getCycleTimeModel().getDiffHours());
        assertEquals(0, output.getCycleTimeModel().getDiffMinutes());
        assertEquals(0, output.getCycleTimeModel().getDiffSeconds());
    }

    @Test
    public void calculate_cycleTime_with_moving_card_should_count_lead_time() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/27 20:54:00"));
        testUtility.moveCard(workflowId, cardId, readyStageId, analysisStageId);
        CycleTimeModel cycleTimeModel = testUtility.calculateCycleTime(workflowId, cardId, readyStageId, analysisStageId);

        assertEquals(1, cycleTimeModel.getDiffDays());
        assertEquals(1, cycleTimeModel.getDiffHours());
        assertEquals(0, cycleTimeModel.getDiffMinutes());
        assertEquals(0, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/29 23:57:10"));
        testUtility.moveCard(workflowId, cardId, analysisStageId, developmentStageId);
        cycleTimeModel = testUtility.calculateCycleTime(workflowId, cardId, readyStageId, developmentStageId);

        assertEquals(3, cycleTimeModel.getDiffDays());
        assertEquals(4, cycleTimeModel.getDiffHours());
        assertEquals(3, cycleTimeModel.getDiffMinutes());
        assertEquals(10, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/30 23:57:10"));
        testUtility.moveCard(workflowId, cardId, developmentStageId, testStageId);
        cycleTimeModel = testUtility.calculateCycleTime(workflowId, cardId, readyStageId, testStageId);

        assertEquals(4, cycleTimeModel.getDiffDays());
        assertEquals(4, cycleTimeModel.getDiffHours());
        assertEquals(3, cycleTimeModel.getDiffMinutes());
        assertEquals(10, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/31 23:57:10"));
        testUtility.moveCard(workflowId, cardId, testStageId, deployStageId);
        cycleTimeModel = testUtility.calculateCycleTime(workflowId, cardId, readyStageId, deployStageId);

        assertEquals(5, cycleTimeModel.getDiffDays());
        assertEquals(4, cycleTimeModel.getDiffHours());
        assertEquals(3, cycleTimeModel.getDiffMinutes());
        assertEquals(10, cycleTimeModel.getDiffSeconds());
    }
}
