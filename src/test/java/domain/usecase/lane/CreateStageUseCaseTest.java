package domain.usecase.lane;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateStageUseCaseTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String workflowId;
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
        assertEquals("Backlog", workflowRepository.findById(workflowId).findLaneById(output.getStageId()).getName());
    }

    @Test
    public void createStageUnderLane() {
        String parentLaneId = testUtility.createTopStage(workflowId, "Backlog");
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Developing");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentLaneId);

        createStageUseCase.execute(input, output);

        assertEquals(1, workflowRepository.findById(workflowId).findLaneById(parentLaneId).getChildAmount());
        assertEquals("Developing", workflowRepository.findById(workflowId).findLaneById(output.getStageId()).getName());
    }
}
