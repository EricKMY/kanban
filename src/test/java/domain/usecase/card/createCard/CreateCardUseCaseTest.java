//package domain.usecase.card.createCard;
//
//import domain.adapter.card.CardRepository;
//import domain.adapter.workflow.WorkflowRepository;
//import domain.model.workflow.Stage;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class CreateCardUseCaseTest {
//
//    private WorkflowRepository workflowRepository;
//    private String workflowId;
//    @Before
//    public void setup() {
//        workflowRepository = new WorkflowRepository();
//        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
//        CreateWorkflowInput input = new CreateWorkflowInput();
//        CreateWorkflowOutput output = new CreateWorkflowOutput();
//
//        input.setBoardId("board00000001");
//        input.setWorkflowName("defaultWorkflow");
//
//        createWorkflowUseCase.execute(input, output);
//
//        workflowId = output.getWorkflowId();
//    }
//
//    @Test
//    public void createCard() {
//        CardRepository cardRepository = new CardRepository();
//        CreateCardUseCase createCardUseCase = new CreateCardUseCase(
//                cardRepository,
//                workflowId);
//
//        CreateCardInput input = new CreateCardInput();
//        CreateCardOutput output = new CreateCardOutput();
//
//        input.setCardName("firstEvent");
//
//        createCardUseCase.execute(input, output);
//        assertEquals('C', cardRepository.findById(output.getCardId()).getCardId().charAt(0));
//    }
//
//}
