package domain.usecase.lane;

import domain.adapter.presenter.lane.stage.create.CreateStagePresenter;
import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.LaneDirection;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateStageUseCaseTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String workflowId;
    private String topStageId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;
    private IDomainEventRepository domainEventRepository;


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

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        topStageId = testUtility.createTopStage(workflowId, "Backlog");
    }

    @Test
    public void create_a_top_Stage() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = createStageUseCase;
        CreateStageOutput output = new CreateStagePresenter();

        input.setStageName("Backlog");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(null);

        createStageUseCase.execute(input, output);

        assertNotNull(output.getStageId());

        Lane lane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(output.getStageId());

        assertEquals("Backlog", lane.getName());
        assertEquals(output.getStageId(), lane.getId());
        assertEquals(0, lane.getChildAmount());
        assertEquals(LaneDirection.HORIZONTAL, lane.getLaneDirection());
    }

    @Test
    public void create_a_Stage_under_top_Stage() {
        String parenStageId = testUtility.createTopStage(workflowId, "Backlog");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = createStageUseCase;
        CreateStageOutput output = new CreateStagePresenter();

        input.setStageName("Developing");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createStageUseCase.execute(input, output);

        assertNotNull(output.getStageId());

        Lane parentLane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(parenStageId);

        Lane childLane = parentLane.findById(output.getStageId());

        assertEquals(1, parentLane.getChildAmount());
        assertEquals(0, childLane.getChildAmount());
        assertEquals("Developing", childLane.getName());
        assertEquals(output.getStageId(), childLane.getId());
        assertEquals(LaneDirection.HORIZONTAL, childLane.getLaneDirection());
    }

    @Test
    public void create_a_Stage_under_Stage() {
        String parenStageId = testUtility.createStage(workflowId, topStageId, "Developing");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = createStageUseCase;
        CreateStageOutput output = new CreateStagePresenter();

        input.setStageName("Team_1");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createStageUseCase.execute(input, output);

        assertNotNull(output.getStageId());

        Lane parentLane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(parenStageId);

        Lane childLane = parentLane.findById(output.getStageId());


        assertEquals(1, parentLane.getChildAmount());
        assertEquals(0, childLane.getChildAmount());
        assertEquals("Team_1", childLane.getName());
        assertEquals(output.getStageId(), childLane.getId());
        assertEquals(LaneDirection.HORIZONTAL, childLane.getLaneDirection());
    }

    @Test
    public void create_a_Stage_under_SwimLane() {
        String parentSwimLaneId = testUtility.createSwimLane(workflowId, topStageId, "Undo");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = createStageUseCase;
        CreateStageOutput output = new CreateStagePresenter();

        input.setStageName("Team_1");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentSwimLaneId);

        createStageUseCase.execute(input, output);

        assertNotNull(output.getStageId());

        Lane parentLane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(parentSwimLaneId);

        Lane childLane = parentLane.findById(output.getStageId());

        assertEquals(1, parentLane.getChildAmount());
        assertEquals(0, childLane.getChildAmount());
        assertEquals("Team_1", childLane.getName());
        assertEquals(output.getStageId(), childLane.getId());
        assertEquals(LaneDirection.HORIZONTAL, childLane.getLaneDirection());

    }

}
