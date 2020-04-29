package domain.usecase;

import com.google.common.eventbus.Subscribe;
import domain.model.workflow.event.WorkflowCreated;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowInput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowOutput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowUseCase;

public class DomainEventHandler {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;

    public DomainEventHandler(IBoardRepository boardRepository,
                              IWorkflowRepository workflowRepository) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
    }

    @Subscribe
    public void handleEvent(WorkflowCreated workflowCreated) {
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository);
        CommitWorkflowInput commitWorkflowInput = new CommitWorkflowInput();
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowOutput();

        commitWorkflowInput.setBoardId(workflowCreated.getBoardId());
        commitWorkflowInput.setWorkflowId(workflowCreated.getWorkflowId());

        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);
    }
//
//    @Subscribe
//    public void handleEvent(CardCreated cardCreated) {
//        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);
//        CommitCardInput commitCardInput = new CommitCardInput();
//        commitCardInput.setCardId(cardCreated.getCardId());
//        commitCardInput.setStageId(cardCreated.getStageId());
//        commitCardInput.setWorkflowId(cardCreated.getWorkflowId());
//        CommitCardOutput commitCardOutput = new CommitCardOutput();
//
//        commitCardUseCase.execute(commitCardInput, commitCardOutput);
//    }

}
