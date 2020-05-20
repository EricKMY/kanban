package domain.usecase.workflow;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.adapter.workflow.createWorkflow.CreateWorkflowPresenter;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWorkflowUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String boardId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository));
        testUtility = new TestUtility(boardRepository, workflowRepository, eventBus);

        boardId = testUtility.createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void create_a_Workflow(){
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);
        CreateWorkflowInput input = (CreateWorkflowInput) createWorkflowUseCase;
        CreateWorkflowOutput output = new CreateWorkflowPresenter();

        input.setBoardId(boardId);
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);

        assertEquals(boardId, workflowRepository.findById(output.getWorkflowId()).getBoardId());
    }

}
