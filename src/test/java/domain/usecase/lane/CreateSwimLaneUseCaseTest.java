package domain.usecase.lane;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.lane.createSwimLane.CreateSwimLaneInput;
import domain.usecase.lane.createSwimLane.CreateSwimLaneOutput;
import domain.usecase.lane.createSwimLane.CreateSwimLaneUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;
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


    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository));
        testUtility = new TestUtility(boardRepository, workflowRepository, eventBus);

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        topStageId = testUtility.createTopStage(workflowId, "Backlog");
    }

    @Test
    public void create_a_SwimLane_under_top_Stage() {
        CreateSwimLaneUseCase createSwimLaneUseCase = new CreateSwimLaneUseCase(workflowRepository, boardRepository);
        CreateSwimLaneInput input = new CreateSwimLaneInput();
        CreateSwimLaneOutput output = new CreateSwimLaneOutput();

        input.setSwimLaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(topStageId);

        createSwimLaneUseCase.execute(input, output);

        assertEquals(1, WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(topStageId)
                                .getChildAmount());

        assertEquals("Urgent", WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(output.getSwimLaneId())
                                        .getName());
    }

    @Test
    public void create_a_SwimLane_under_Stage() {
        String parenStageId = testUtility.createStage(workflowId, topStageId, "Developing");

        CreateSwimLaneUseCase createSwimLaneUseCase = new CreateSwimLaneUseCase(workflowRepository, boardRepository);
        CreateSwimLaneInput input = new CreateSwimLaneInput();
        CreateSwimLaneOutput output = new CreateSwimLaneOutput();

        input.setSwimLaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createSwimLaneUseCase.execute(input, output);

        assertEquals(1, WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());

        assertEquals("Urgent", WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getSwimLaneId())
                                        .getName());

    }

    @Test
    public void create_a_SwimLane_under_SwimLane() {

        String parenStageId = testUtility.createSwimLane(workflowId, topStageId, "Undo");

        CreateSwimLaneUseCase createSwimLaneUseCase = new CreateSwimLaneUseCase(workflowRepository, boardRepository);
        CreateSwimLaneInput input = new CreateSwimLaneInput();
        CreateSwimLaneOutput output = new CreateSwimLaneOutput();

        input.setSwimLaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createSwimLaneUseCase.execute(input, output);

        assertEquals(1, WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());

        assertEquals("Urgent", WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId))
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getSwimLaneId())
                                        .getName());
    }
}
