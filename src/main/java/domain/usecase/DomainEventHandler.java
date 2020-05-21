package domain.usecase;

import com.google.common.eventbus.Subscribe;
import domain.adapter.workflow.commitWorkflow.CommitWorkflowPresenter;
import domain.model.DomainEventBus;
import domain.model.card.event.CardCreated;
import domain.model.workflow.event.WorkflowCreated;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowInput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowOutput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowUseCase;

public class DomainEventHandler {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public DomainEventHandler(IBoardRepository boardRepository,
                              IWorkflowRepository workflowRepository,
                              DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void handleEvent(WorkflowCreated workflowCreated) {
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository,eventBus);
        CommitWorkflowInput commitWorkflowInput = (CommitWorkflowInput) commitWorkflowUseCase;
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowPresenter();

        commitWorkflowInput.setBoardId(workflowCreated.getBoardId());
        commitWorkflowInput.setWorkflowId(workflowCreated.getWorkflowId());

        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);
    }

    @Subscribe
    public void handleEvent(CardCreated cardCreated) {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);
        CommitCardInput commitCardInput = new CommitCardInput();
        commitCardInput.setCardId(cardCreated.getCardId());
        commitCardInput.setLaneId(cardCreated.getLaneId());
        commitCardInput.setWorkflowId(cardCreated.getWorkflowId());
        CommitCardOutput commitCardOutput = new CommitCardOutput();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }

}
