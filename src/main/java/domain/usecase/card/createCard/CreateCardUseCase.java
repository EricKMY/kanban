package domain.usecase.card.createCard;

import domain.adapter.card.CardRepository;
import domain.model.card.Card;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.repository.IWorkflowRepository;

public class CreateCardUseCase {
    private IWorkflowRepository workflowRepository;
    private CardRepository cardRepository;

    public CreateCardUseCase(IWorkflowRepository workflowRepository, CardRepository cardRepository) {
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
    }

    public void execute(CreateCardInput input, CreateCardOutput output) {
        Card card = new Card(input.getCardName(), input.getWorkflowId());
        cardRepository.save(card);

        output.setCardId(card.getId());

        // commit card
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);

        CommitCardInput commitCardInput = new CommitCardInput();
        CommitCardOutput commitCardOutput = new CommitCardOutput();

        commitCardInput.setWorkflowId(input.getWorkflowId());
        commitCardInput.setLaneId(input.getLaneId());
        commitCardInput.setCardId(card.getId());

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }
}
