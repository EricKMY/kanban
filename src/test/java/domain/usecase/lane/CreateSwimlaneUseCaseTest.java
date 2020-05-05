package domain.usecase.lane;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.lane.createSwimlane.CreateSwimlaneInput;
import domain.usecase.lane.createSwimlane.CreateSwimlaneOutput;
import domain.usecase.lane.createSwimlane.CreateSwimlaneUseCase;
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
        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository, boardRepository);
        CreateSwimlaneInput input = new CreateSwimlaneInput();
        CreateSwimlaneOutput output = new CreateSwimlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(topStageId);

        createSwimlaneUseCase.execute(input, output);

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

        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository, boardRepository);
        CreateSwimlaneInput input = new CreateSwimlaneInput();
        CreateSwimlaneOutput output = new CreateSwimlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createSwimlaneUseCase.execute(input, output);

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

        String parenStageId = testUtility.createSwimLane(workflowId, topStageId, "Undo");

        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository, boardRepository);
        CreateSwimlaneInput input = new CreateSwimlaneInput();
        CreateSwimlaneOutput output = new CreateSwimlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parenStageId);

        createSwimlaneUseCase.execute(input, output);

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
