package domain.usecase.card.commitCard;

import domain.adapter.card.CardRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.adapter.workflow.WorkflowRepository;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.stage.createStage.CreateStageInput;
import domain.usecase.stage.createStage.CreateStageOutput;
import domain.usecase.stage.createStage.CreateStageUseCase;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitCardUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private CardRepository cardRepository;
    private String workflowId;
    private String stageId;
    private String cardId;


    @Before
    public void setup() {
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardRepository();

        workflowId = createWorkflow("board00000001", "defaultWorkflow");
        stageId = createStage(workflowId, "developing");
        cardId = createCard(workflowId, "Design domain model");
    }

    @Test
    public void commitCard() {
        CardRepository cardRepository = new CardRepository();
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(
                workflowRepository,
                cardRepository);

        CommitCardInput input = new CommitCardInput();
        CommitCardOutput output = new CommitCardOutput();

        input.setWorkflowId(workflowId);
        input.setStageId(stageId);
        input.setCardId(cardId);

        commitCardUseCase.execute(input, output);
        assertEquals(stageId, workflowRepository.findById(workflowId).findLaneByCardId(cardId).getLaneId());
    }


    private String createCard(String workflowId, String cardName) {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, workflowId);
        CreateCardInput input = new CreateCardInput();
        CreateCardOutput output = new CreateCardOutput();

        input.setCardName(cardName);

        createCardUseCase.execute(input, output);
        return output.getCardId();
    }


    private String createWorkflow(String boardId, String workflowName) {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);

        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();

    }


    private String createStage(String workflowId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setWorkflowId(workflowId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }
}
