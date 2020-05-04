package domain.usecase.lane;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import domain.usecase.repository.IBoardRepository;
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
    public void createTopStage() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Backlog");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(null);

        createStageUseCase.execute(input, output);

        assertNotNull(output.getStageId());
        assertEquals("Backlog", workflowRepository
                                        .findById(workflowId)
                                        .findLaneById(output.getStageId())
                                        .getName());
    }

    @Test
    public void createStageUnderTopStage() {
        String parenStageId = testUtility.createTopStage(workflowId, "Backlog");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Developing");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createStageUseCase.execute(input, output);

        assertEquals(1, workflowRepository
                                .findById(workflowId)
                                .findLaneById(parenStageId)
                                .getChildAmount());

        assertEquals("Developing", workflowRepository
                                            .findById(workflowId)
                                            .findLaneById(output.getStageId())
                                            .getName());
    }

    @Test
    public void createStageUnderStage() {
        String parenStageId = testUtility.createStage(workflowId, topStageId, "Developing");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Team_1");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createStageUseCase.execute(input, output);

        assertEquals(1, workflowRepository
                                .findById(workflowId)
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());


        assertEquals("Team_1", workflowRepository
                                        .findById(workflowId)
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getStageId())
                                        .getName());

    }

    @Test
    public void createStageUnderSwimeLane() {
        String parenStageId = testUtility.createSwimLane(workflowId, topStageId, "Undo");

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Team_1");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createStageUseCase.execute(input, output);

        assertEquals(1, workflowRepository
                                .findById(workflowId)
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());


        assertEquals("Team_1", workflowRepository
                                        .findById(workflowId)
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getStageId())
                                        .getName());
    }

}
