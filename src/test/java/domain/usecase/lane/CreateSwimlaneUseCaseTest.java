package domain.usecase.lane;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import domain.usecase.lane.createSwinlane.CreateSwinlaneInput;
import domain.usecase.lane.createSwinlane.CreateSwinlaneOutput;
import domain.usecase.lane.createSwinlane.CreateSwinlaneUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateSwimlaneUseCaseTest {
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
    public void createSwimlaneUnderTopStage() {
        CreateSwinlaneUseCase createSwinlaneUseCase = new CreateSwinlaneUseCase(workflowRepository, boardRepository);
        CreateSwinlaneInput input = new CreateSwinlaneInput();
        CreateSwinlaneOutput output = new CreateSwinlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(topStageId);

        createSwinlaneUseCase.execute(input, output);

        assertEquals(1, workflowRepository
                                .findById(workflowId)
                                .findLaneById(topStageId)
                                .getChildAmount());

        assertEquals("Urgent", workflowRepository
                                        .findById(workflowId)
                                        .findLaneById(output.getSwinlaneId())
                                        .getName());
    }

    @Test
    public void createSwimlaneUnderStage() {
        String parenStageId = testUtility.createStage(workflowId, topStageId, "Developing");

        CreateSwinlaneUseCase createSwinlaneUseCase = new CreateSwinlaneUseCase(workflowRepository, boardRepository);
        CreateSwinlaneInput input = new CreateSwinlaneInput();
        CreateSwinlaneOutput output = new CreateSwinlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createSwinlaneUseCase.execute(input, output);

        assertEquals(1, workflowRepository
                                .findById(workflowId)
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());

        assertEquals("Urgent", workflowRepository
                                        .findById(workflowId)
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getSwinlaneId())
                                        .getName());

    }

    @Test
    public void createSwimlaneUnderSwimlane() {

        String parenStageId = testUtility.createSwimeLane(workflowId, topStageId, "Undo");

        CreateSwinlaneUseCase createSwinlaneUseCase = new CreateSwinlaneUseCase(workflowRepository, boardRepository);
        CreateSwinlaneInput input = new CreateSwinlaneInput();
        CreateSwinlaneOutput output = new CreateSwinlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createSwinlaneUseCase.execute(input, output);

        assertEquals(1, workflowRepository
                                .findById(workflowId)
                                .findLaneById(topStageId)
                                .findById(parenStageId)
                                .getChildAmount());

        assertEquals("Urgent", workflowRepository
                                        .findById(workflowId)
                                        .findLaneById(topStageId)
                                        .findById(parenStageId)
                                        .findById(output.getSwinlaneId())
                                        .getName());
    }
}
