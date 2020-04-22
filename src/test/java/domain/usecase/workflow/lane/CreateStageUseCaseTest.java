package domain.usecase.workflow.lane;

import domain.adapter.board.BoardRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.adapter.workflow.WorkflowRepository;
import domain.model.workflow.Lane;
import domain.model.workflow.SwimLane;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateStageUseCaseTest {
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
    public void createTopStage() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Backlog");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(null);

        createStageUseCase.execute(input, output);

        assertEquals('S', output.getStageId().charAt(0));
        assertEquals("Backlog", workflowRepository.findById(workflowId).findLaneById(output.getStageId()).getLaneName());
    }

    @Test
    public void createStageUnderLane() {
        String parentLaneId = createTopStage(workflowId, "Backlog");
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setStageName("Developing");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentLaneId);

        createStageUseCase.execute(input, output);

        assertEquals(1, workflowRepository.findById(workflowId).findLaneById(parentLaneId).getChildAmount());
        assertEquals("Developing", workflowRepository.findById(workflowId).findLaneById(output.getStageId()).getLaneName());
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

        input.setStageName("Backlog");
        input.setWorkflowId(workflowId);
        input.setParentLaneId(null);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }
}
