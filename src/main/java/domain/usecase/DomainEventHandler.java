package domain.usecase;

import com.google.common.eventbus.Subscribe;
import domain.adapter.presenter.card.commit.CommitCardPresenter;
import domain.adapter.presenter.workflow.commit.CommitWorkflowPresenter;
import domain.adapter.presenter.card.uncommit.UncommitCardPresenter;
import domain.model.DomainEventBus;
import domain.model.aggregate.card.event.CardCreated;
import domain.model.aggregate.card.event.CardMoved;
import domain.model.aggregate.workflow.event.WorkflowCreated;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.card.uncommitCard.UncommitCardInput;
import domain.usecase.card.uncommitCard.UncommitCardOutput;
import domain.usecase.card.uncommitCard.UncommitCardUseCase;
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
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowInput commitWorkflowInput = (CommitWorkflowInput) commitWorkflowUseCase;
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowPresenter();

        commitWorkflowInput.setBoardId(workflowCreated.getBoardId());
        commitWorkflowInput.setWorkflowId(workflowCreated.getWorkflowId());

        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);
    }

    @Subscribe
    public void handleEvent(CardCreated cardCreated) {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);
        CommitCardInput commitCardInput = (CommitCardInput) commitCardUseCase;
        commitCardInput.setCardId(cardCreated.getCardId());
        commitCardInput.setLaneId(cardCreated.getLaneId());
        commitCardInput.setWorkflowId(cardCreated.getWorkflowId());
        CommitCardOutput commitCardOutput = new CommitCardPresenter();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }

    @Subscribe
    public void handleEvent(CardMoved cardMoved) {
        UncommitCardUseCase uncommitCardUseCase = new UncommitCardUseCase(workflowRepository, eventBus);
        UncommitCardInput uncommitCardInput = (UncommitCardInput) uncommitCardUseCase;
        uncommitCardInput.setCardId(cardMoved.getCardId());
        uncommitCardInput.setLaneId(cardMoved.getOriginalLaneId());
        uncommitCardInput.setWorkflowId(cardMoved.getWorkflowId());
        UncommitCardOutput uncommitCardOutput = new UncommitCardPresenter();

        uncommitCardUseCase.execute(uncommitCardInput, uncommitCardOutput);

        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);
        CommitCardInput commitCardInput = (CommitCardInput) commitCardUseCase;
        commitCardInput.setCardId(cardMoved.getCardId());
        commitCardInput.setLaneId(cardMoved.getTargetLaneId());
        commitCardInput.setWorkflowId(cardMoved.getWorkflowId());
        CommitCardOutput commitCardOutput = new CommitCardPresenter();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }

}
