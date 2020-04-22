package domain.usecase.workflow.lane.createSwinlane;

import domain.adapter.board.BoardRepository;
import domain.adapter.workflow.WorkflowRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import domain.usecase.workflow.lane.createStage.CreateStageInput;
import domain.usecase.workflow.lane.createStage.CreateStageOutput;
import domain.usecase.workflow.lane.createStage.CreateStageUseCase;
import domain.usecase.workflow.lane.createSwinlane.CreateSwinlaneInput;
import domain.usecase.workflow.lane.createSwinlane.CreateSwinlaneOutput;
import domain.usecase.workflow.lane.createSwinlane.CreateSwinlaneUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateSwinlaneUseCaseTest {
    private BoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String workflowId;


    @Before
    public void setup() {
        boardRepository = new BoardRepository();
        workflowRepository = new WorkflowRepository();

        String boardId = createBoard("kanban777", "kanban");
        workflowId = createWorkflow(boardId, "defaultWorkflow");
    }

    @Test
    public void createSwinlane() {
        String parentLaneId = createTopStage(workflowId, "Backlog");
        CreateSwinlaneUseCase createSwinlaneUseCase = new CreateSwinlaneUseCase(workflowRepository, boardRepository);
        CreateSwinlaneInput input = new CreateSwinlaneInput();
        CreateSwinlaneOutput output = new CreateSwinlaneOutput();

        input.setSwinlaneName("Urgent");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentLaneId);

        createSwinlaneUseCase.execute(input, output);

        assertEquals(1, workflowRepository.findById(workflowId).findLaneById(parentLaneId).getChildAmount());
        assertEquals("Urgent", workflowRepository.findById(workflowId).findLaneById(output.getSwinlaneId()).getLaneName());
    }


    private String createWorkflow(String boardId, String workflowName) {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardRepository);

        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();

    }

    private String createBoard(String username, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername(username);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }


    private String createTopStage(String workflowId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Developing");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(null);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }
}
