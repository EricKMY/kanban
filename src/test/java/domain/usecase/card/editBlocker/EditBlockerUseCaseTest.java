//package domain.usecase.card.editBlocker;
//
//import domain.adapter.card.CardRepository;
//import domain.adapter.workflow.WorkflowRepository;
//import domain.model.card.Card;
//import domain.usecase.card.createCard.CreateCardInput;
//import domain.usecase.card.createCard.CreateCardOutput;
//import domain.usecase.card.createCard.CreateCardUseCase;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//public class EditBlockerUseCaseTest {
//    private CardRepository cardRepository;
//    private Card card;
//    private WorkflowRepository workflowRepository;
//    private String workflowId;
//
//    @Before
//    public void setup() {
//
//        workflowRepository = new WorkflowRepository();
//        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
//        CreateWorkflowInput workflowInput = new CreateWorkflowInput();
//        CreateWorkflowOutput workflowOutput = new CreateWorkflowOutput();
//
//        workflowInput.setBoardId("board00000001");
//        workflowInput.setWorkflowName("defaultWorkflow");
//
//        createWorkflowUseCase.execute(workflowInput, workflowOutput);
//        workflowId = workflowOutput.getWorkflowId();
//
//        cardRepository = new CardRepository();
//        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, workflowId);
//        CreateCardInput cardInput = new CreateCardInput();
//        CreateCardOutput cardOutput = new CreateCardOutput();
//
//        cardInput.setCardName("Design domain model");
//
//        createCardUseCase.execute(cardInput, cardOutput);
//        String cardId = cardOutput.getCardId();
//
//        card = cardRepository.findById(cardId);
//        assertNotNull(card);
//    }
//
//    @Test
//    public void editBlocker() {
//        EditBlockerUseCase editBlockerUseCase = new EditBlockerUseCase(cardRepository);
//        EditBlockerInput input = new EditBlockerInput();
//        EditBlockerOutput output = new EditBlockerOutput();
//
//        input.setCardId(card.getCardId());
//        input.setBlocker("Wait for hardware support.");
//
//        editBlockerUseCase.execute(input, output);
//        assertEquals("Wait for hardware support.", cardRepository.findById(card.getCardId()).getBlocker());
//    }
//}
