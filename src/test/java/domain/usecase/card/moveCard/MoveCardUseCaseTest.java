//package domain.usecase.card.moveCard;
//
//import domain.adapter.card.CardRepository;
//import domain.adapter.workflow.WorkflowInMemoryRepository;
//import domain.model.card.Card;
//import domain.usecase.card.createCard.CreateCardInput;
//import domain.usecase.card.createCard.CreateCardOutput;
//import domain.usecase.card.createCard.CreateCardUseCase;
//import domain.usecase.card.editBlocker.EditBlockerInput;
//import domain.usecase.card.editBlocker.EditBlockerOutput;
//import domain.usecase.card.editBlocker.EditBlockerUseCase;
//import domain.usecase.repository.IWorkflowRepository;
//import domain.usecase.stage.createStage.CreateStageInput;
//import domain.usecase.stage.createStage.CreateStageOutput;
//import domain.usecase.stage.createStage.CreateStageUseCase;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
//import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//public class MoveCardUseCaseTest {
//    private IWorkflowRepository workflowRepository;
//    private CardRepository cardRepository;
//    private String cardId;
//    private String workflowId;
//    private String originStageId, targetStageId;
//
//
//
//    @Before
//    public void setup() {
//        workflowRepository = new WorkflowInMemoryRepository();
//        cardRepository = new CardRepository();
//
//        String workflowId = createWorkflow("board00000001", "defaultWorkflow");
//        String originStageId = createStage(workflowId, "developing");
//        String targetStageId = createStage(workflowId, "testing");
//        String cardId = createCard(workflowId, "Design domain model");
//        workflowRepository.findById()
//    }
//
//    private String createCard(String workflowId, String cardName) {
//        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, workflowId);
//        CreateCardInput input = new CreateCardInput();
//        CreateCardOutput output = new CreateCardOutput();
//
//        input.setCardName(cardName);
//
//        createCardUseCase.execute(input, output);
//        return output.getCardId();
//    }
//
//    private String createWorkflow(String boardId, String workflowName) {
//        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
//
//        CreateWorkflowInput input = new CreateWorkflowInput();
//        CreateWorkflowOutput output = new CreateWorkflowOutput();
//        input.setBoardId(boardId);
//        input.setWorkflowName(workflowName);
//
//        createWorkflowUseCase.execute(input, output);
//        return output.getWorkflowId();
//
//    }
//
//    private String createStage(String workflowId, String stageName) {
//        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
//        CreateStageInput input = new CreateStageInput();
//        CreateStageOutput output = new CreateStageOutput();
//
//        input.setWorkflowId(workflowId);
//        input.setStageName(stageName);
//
//        createStageUseCase.execute(input, output);
//
//        return output.getStageId();
//    }
//
//    @Test
//    public void moveCardWithoutBlocker() {
//    }
//
//    @Test
//    public void moveCardWithBlocker() {
//    }
//}
