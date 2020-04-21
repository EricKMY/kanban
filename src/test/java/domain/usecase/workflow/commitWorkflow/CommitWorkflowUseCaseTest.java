//package domain.usecase;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertTrue;
//
//public class CommitWorkflowUseCaseTest {
//    private BoardRepository boardRepository;
//    private WorkflowRepository workflowRepository;
//    private CreateWorkflowUseCase createWorkflowUseCase;
//    private CreateWorkflowInput createWorkflowInput;
//    private CreateWorkflowOutput createWorkflowOutput;
//
//    @Before
//    public void testSetup() {
//        workflowRepository = new WorkflowRepository();
//        createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
//        createWorkflowInput = new CreateWorkflowInput();
//        createWorkflowOutput = new CreateWorkflowOutput();
//
//        createWorkflowInput.setWorkflowName("Workflow1");
//
//        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);
//    }
//
//    @Test
//    public void commitWorkflowUseCase() {
////        boardRepository = new BoardRepository();
//        CommitWorkflowInput commitWorkflowInput = new CommitWorkflowInput(
//                createWorkflowOutput.getBoardId(),
//                createWorkflowOutput.getWorkflowId()
//        );
//        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowOutput();
//        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase();
//
//        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);
//
//        assertTrue(commitWorkflowOutput.getResult());
//    }
//}
