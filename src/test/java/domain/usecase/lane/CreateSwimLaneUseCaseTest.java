package domain.usecase.lane;

import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.lane.createSwimLane.CreateSwimLaneInput;
import domain.usecase.lane.createSwimLane.CreateSwimLaneOutput;
import domain.usecase.lane.createSwimLane.CreateSwimLaneUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateSwimLaneUseCaseTest {
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
    public void create_a_SwimLane_under_top_Stage() {
        CreateSwimLaneUseCase createSwimLaneUseCase = new CreateSwimLaneUseCase(workflowRepository, eventBus);
        CreateSwimLaneInput input = new CreateSwimLaneInput();
        CreateSwimLaneOutput output = new CreateSwimLaneOutput();

        input.setSwimLaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(topStageId);

        createSwimLaneUseCase.execute(input, output);

        assertEquals(1, WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(topStageId)
                                .getChildAmount());

        assertEquals("Urgent", WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(output.getSwimLaneId())
                                        .getName());
    }

    @Test
    public void create_a_SwimLane_under_Stage() {
        String parenStageId = testUtility.createStage(workflowId, topStageId, "Developing");

        CreateSwimLaneUseCase createSwimLaneUseCase = new CreateSwimLaneUseCase(workflowRepository, eventBus);
        CreateSwimLaneInput input = new CreateSwimLaneInput();
        CreateSwimLaneOutput output = new CreateSwimLaneOutput();

        input.setSwimLaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createSwimLaneUseCase.execute(input, output);

        assertEquals(1, WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());

        assertEquals("Urgent", WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getSwimLaneId())
                                        .getName());

    }

    @Test
    public void create_a_SwimLane_under_SwimLane() {

        String parenStageId = testUtility.createSwimLane(workflowId, topStageId, "Undo");

        CreateSwimLaneUseCase createSwimLaneUseCase = new CreateSwimLaneUseCase(workflowRepository, eventBus);
        CreateSwimLaneInput input = new CreateSwimLaneInput();
        CreateSwimLaneOutput output = new CreateSwimLaneOutput();

        input.setSwimLaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createSwimLaneUseCase.execute(input, output);

        assertEquals(1, WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());

        assertEquals("Urgent", WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getSwimLaneId())
                                        .getName());
    }
}
