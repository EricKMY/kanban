package domain.usecase.lane;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.lane.createSwinlane.CreateSwinlaneInput;
import domain.usecase.lane.createSwinlane.CreateSwinlaneOutput;
import domain.usecase.lane.createSwinlane.CreateSwinlaneUseCase;
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
    private BoardInMemoryRepository boardInMemoryRepository;
    private IWorkflowRepository workflowRepository;
    private String workflowId;


    @Before
    public void setup() {
        boardInMemoryRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();

        String boardId = createBoard("kanban777", "kanban");
        workflowId = createWorkflow(boardId, "defaultWorkflow");
    }

    @Test
    public void createSwinlane() {
        String parentLaneId = createTopStage(workflowId, "Backlog");
        CreateSwinlaneUseCase createSwinlaneUseCase = new CreateSwinlaneUseCase(workflowRepository, boardInMemoryRepository);
        CreateSwinlaneInput input = new CreateSwinlaneInput();
        CreateSwinlaneOutput output = new CreateSwinlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentLaneId);

        createSwinlaneUseCase.execute(input, output);

        assertEquals(1, workflowRepository.findById(workflowId).findLaneById(parentLaneId).getChildAmount());
        assertEquals("Urgent", workflowRepository.findById(workflowId).findLaneById(output.getSwinlaneId()).getName());
    }


    private String createWorkflow(String boardId, String workflowName) {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardInMemoryRepository);

        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();

    }

    private String createBoard(String username, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardInMemoryRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername(username);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }


    private String createTopStage(String workflowId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardInMemoryRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Developing");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(null);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }
}
