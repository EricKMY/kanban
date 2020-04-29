package domain.usecase.lane;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.lane.createSwinlane.CreateSwinlaneInput;
import domain.usecase.lane.createSwinlane.CreateSwinlaneOutput;
import domain.usecase.lane.createSwinlane.CreateSwinlaneUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateSwinlaneUseCaseTest {
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
    public void createSwinlane() {
        CreateSwinlaneUseCase createSwinlaneUseCase = new CreateSwinlaneUseCase(workflowRepository, boardRepository);
        CreateSwinlaneInput input = new CreateSwinlaneInput();
        CreateSwinlaneOutput output = new CreateSwinlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(topStageId);

        createSwinlaneUseCase.execute(input, output);

        assertEquals(1, workflowRepository.findById(workflowId).findLaneById(topStageId).getChildAmount());
        assertEquals("Urgent", workflowRepository.findById(workflowId).findLaneById(output.getSwinlaneId()).getName());
    }
}
