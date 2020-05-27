package domain.usecase.lane;

import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateStageUseCaseTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String workflowId;
    private String topStageId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
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

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        topStageId = testUtility.createTopStage(workflowId, "Backlog");
    }

    @Test
    public void create_a_top_Stage() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Backlog");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(null);

        createStageUseCase.execute(input, output);

        assertEquals("Backlog", WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(output.getStageId())
                                        .getName());
    }

    @Test
    public void create_a_Stage_under_top_Stage() {
        String parenStageId = testUtility.createTopStage(workflowId, "Backlog");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Developing");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createStageUseCase.execute(input, output);

        assertEquals(1, WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(parenStageId)
                                .getChildAmount());

        assertEquals("Developing", WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                            .findLaneById(output.getStageId())
                                            .getName());
    }

    @Test
    public void create_a_Stage_under_Stage() {
        String parenStageId = testUtility.createStage(workflowId, topStageId, "Developing");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Team_1");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createStageUseCase.execute(input, output);

        assertEquals(1, WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());


        assertEquals("Team_1", WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getStageId())
                                        .getName());

    }

    @Test
    public void create_a_Stage_under_SwimLane() {
        String parenStageId = testUtility.createSwimLane(workflowId, topStageId, "Undo");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Team_1");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createStageUseCase.execute(input, output);

        assertEquals(1, WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());


        assertEquals("Team_1", WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getStageId())
                                        .getName());
    }

}
